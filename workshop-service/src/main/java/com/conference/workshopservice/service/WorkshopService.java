package com.conference.workshopservice.service;

import com.conference.workshopservice.dto.SpeakerVO;
import com.conference.workshopservice.dto.SuccessResponse;
import com.conference.workshopservice.entity.Speaker;
import com.conference.workshopservice.entity.Workshop;
import com.conference.workshopservice.exception.ResourceNotFoundException;
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
                                                            .peek(this::retrieveSpeakerDetailFromService)
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
//    @Transactional
//    public AttendeeTicket register(AttendeeTicketClientDTO attendeeTicketClientDTO) {
//        Workshop workshop = adjustWorkshopCapacity(attendeeTicketClientDTO);
//        AttendeeTicket attendeeTicket = createAttendTicket(workshop, attendeeTicketClientDTO);
//
//        return attendeeTicket;
//    }
//
//    private Workshop adjustWorkshopCapacity(AttendeeTicketClientDTO attendeeTicketClientDTO) {
//        Workshop workshop = getWorkshop(attendeeTicketClientDTO.getWorkshopId());
//
//        Integer capacity = workshop.getCapacity();
//        if (!(capacity > 0)) {
//            throw new WorkshopNoCapacityException(workshop.getWorkshopId());
//        }
//
//        workshop.setCapacity(capacity - 1);
//
//        return repository.save(workshop);
//    }
//
//    private AttendeeTicket createAttendTicket(Workshop workshop, AttendeeTicketClientDTO attendeeTicketClientDTO) {
//        return attendeeTicketService.createAttendTicket(workshop, attendeeTicketClientDTO);
//    }

    public SpeakerVO retrieveSpeakerDetailFromService(Long speakerId) {
        return restService.getForObject("http://SPEAKER-SERVICE/speakers/{1}",
                new ParameterizedTypeReference<SuccessResponse<SpeakerVO>>() {},
                speakerId
        ).getData();
    }
}
