package com.conference.workshopservice.service;

import com.conference.workshopservice.dto.RegistrationDTO;
import com.conference.workshopservice.dto.RegistrationVO;
import com.conference.workshopservice.dto.SpeakerVO;
import com.conference.workshopservice.dto.SuccessResponse;
import com.conference.workshopservice.entity.Registration;
import com.conference.workshopservice.entity.Speaker;
import com.conference.workshopservice.entity.Workshop;
import com.conference.workshopservice.exception.ResourceNotFoundException;
import com.conference.workshopservice.exception.WorkshopNoCapacityException;
import com.conference.workshopservice.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkshopService {
    @Autowired
    private WorkshopRepository repository;

    @Autowired
    private RestService restService;

    @Transactional(readOnly = true)
    public List<Workshop> getAllWorkshops() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Workshop getWorkshop(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Workshop"));
    }

    public Workshop createWorkshop(Workshop workshop) {
        return repository.save(workshop);
    }

    public Workshop updateWorkshop(Long id, Workshop workshop) {
        Workshop existingWorkshop = getWorkshop(id);
        existingWorkshop.setWorkshopName(workshop.getWorkshopName());
        existingWorkshop.setCapacity(workshop.getCapacity());
        existingWorkshop.setDescription(workshop.getDescription());
        existingWorkshop.setRequirements(workshop.getRequirements());
        existingWorkshop.setRoom(workshop.getRoom());
        return repository.save(existingWorkshop);
    }

    public void deleteWorkshop(Long id) {
        getWorkshop(id);
        repository.deleteById(id);
    }

    public Workshop updateWorkshopSpeakers(Long id, Set<Long> speakerIds) {
        Workshop existingWorkshop = getWorkshop(id);

        Set<Speaker> existingWorkshopSpeakers = existingWorkshop.getSpeakers();

        Set<Speaker> expectedWorkshopSpeakers = speakerIds.stream()
                                                            .peek(this::retrieveSpeakerFromService)
                                                            .map(speakerId -> {
                                                                    Speaker speaker = new Speaker();
                                                                    speaker.setSpeakerId(speakerId);
                                                                    speaker.setWorkshop(existingWorkshop);
                                                                    return speaker;
                                                            })
                                                            .collect(Collectors.toSet());
        existingWorkshopSpeakers.removeIf(speaker -> !expectedWorkshopSpeakers.contains(speaker));
        existingWorkshopSpeakers.addAll(expectedWorkshopSpeakers);

        existingWorkshop.setSpeakers(existingWorkshopSpeakers);
        return repository.save(existingWorkshop);
    }

//    @OptimisticRetry(maxRetryCount = 20)
    public RegistrationVO registerWorkshop(Long workshopId, RegistrationDTO registrationDTO) {
        Workshop workshop = getWorkshop(workshopId);                                                                    // get workshop
//        Workshop workshop = repository.findByIdWithLock(workshopId).orElseThrow(() -> new ResourceNotFoundException("ws"));

        if(!verifyWorkshopCapacity(workshop)) {                                                                         // verify workshop capacity
            throw new WorkshopNoCapacityException(workshop.getWorkshopId());
        }

        updateWorkshopCapacity(workshop);                                                                               // update workshop capacity

        RegistrationVO registrationVO = createAttendTicket(registrationDTO);                                            // create attendeeTicket

        updateWorkshopRegistration(workshop, registrationVO);                                                           // update workshop registration

        repository.save(workshop);
        return registrationVO;
    }

    private boolean verifyWorkshopCapacity(Workshop workshop) {
        return workshop.getCapacity() > 0;
    }

    private void updateWorkshopCapacity(Workshop workshop) {
        workshop.setCapacity(workshop.getCapacity() - 1);
//        repository.saveAndFlush(workshop);
    }

    private void updateWorkshopRegistration(Workshop workshop, RegistrationVO registrationVO) {
        Registration registration = new Registration();
        registration.setAttendeeTicketId(registrationVO.getAttendeeTicketId());

        workshop.addRegistration(registration);

//        repository.save(workshop);
    }

    private RegistrationVO createAttendTicket(RegistrationDTO registrationDTO) {
        return createAttendeeTicketFromService(registrationDTO);
    }

    public SpeakerVO retrieveSpeakerFromService(Long speakerId) {
        return restService.getForObject("http://SPEAKER-SERVICE/speakers/{1}",
                new ParameterizedTypeReference<SuccessResponse<SpeakerVO>>() {},
                speakerId
        ).getData();
    }

    public RegistrationVO retrieveAttendeeTicketFromService(Long attendeeTicketId) {
        return restService.getForObject("http://ATTENDEE-TICKET-SERVICE/attendeeTickets/{1}",
                new ParameterizedTypeReference<SuccessResponse<RegistrationVO>>() {},
                attendeeTicketId
        ).getData();
    }

    public RegistrationVO createAttendeeTicketFromService(RegistrationDTO registrationDTO) {
        return restService.postForObject("http://ATTENDEE-TICKET-SERVICE/attendeeTickets",
                new ParameterizedTypeReference<SuccessResponse<RegistrationVO>>() {},
                registrationDTO
        ).getData();
    }
}
