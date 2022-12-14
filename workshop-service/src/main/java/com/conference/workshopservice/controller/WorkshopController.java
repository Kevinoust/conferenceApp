package com.conference.workshopservice.controller;

import com.conference.workshopservice.dto.*;
import com.conference.workshopservice.entity.Registration;
import com.conference.workshopservice.entity.Speaker;
import com.conference.workshopservice.entity.Workshop;
import com.conference.workshopservice.service.WorkshopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {
    @Autowired
    private WorkshopService service;

    @GetMapping
    public SuccessResponse<?> getAllWorkshops() {
        List<Workshop> workshops = service.getAllWorkshops();
        return new SuccessResponse<>(OK, workshops.stream().map(this::entityToSumVO).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public SuccessResponse<?> getWorkshop(@PathVariable Long id) {
        Workshop workshop = service.getWorkshop(id);
        return new SuccessResponse<>(OK, entityToDtlVO(workshop));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public SuccessResponse<?> createWorkshop(@Valid @RequestBody WorkshopDTO workshopDTO) {
        Workshop workshop = dtoToEntity(workshopDTO);
        return new SuccessResponse<>(CREATED, service.createWorkshop(workshop));
    }

    @PutMapping("{id}")
    public SuccessResponse<?> updateWorkshop(@PathVariable Long id, @Valid @RequestBody WorkshopDTO workshopDTO) {
        Workshop workshop = dtoToEntity(workshopDTO);
        return new SuccessResponse<>(OK, service.updateWorkshop(id, workshop));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public SuccessResponse<?> deleteWorkshop(@PathVariable Long id) {
        service.deleteWorkshop(id);
        return new SuccessResponse<>(NO_CONTENT, "");
    }

    @PutMapping("{id}/speakers")
    public SuccessResponse<?> updateWorkshopSpeakers(@PathVariable Long id, @RequestBody Set<Long> speakerIds) {
        Workshop workshop = service.updateWorkshopSpeakers(id, speakerIds);
        return new SuccessResponse<>(OK, entityToDtlVO(workshop));
    }

    @PostMapping("{id}/registration")
    public SuccessResponse<?> registerWorkshop(@PathVariable Long id, @RequestBody RegistrationDTO registrationDTO) {
        return new SuccessResponse<>(OK, service.registerWorkshop(id, registrationDTO));
    }

    private WorkshopSumVO entityToSumVO(Workshop workshop) {
        WorkshopSumVO workshopSumVO = new WorkshopSumVO();
        BeanUtils.copyProperties(workshop, workshopSumVO);
        return workshopSumVO;
    }

    private WorkshopDtlVO entityToDtlVO(Workshop workshop) {
        WorkshopDtlVO workshopDtlVO = new WorkshopDtlVO();
        BeanUtils.copyProperties(workshop, workshopDtlVO);

        Set<SpeakerVO> speakerVOs = workshop.getSpeakers().stream()
                                                            .map(Speaker::getSpeakerId)
                                                            .map(service::retrieveSpeakerFromService)
                                                            .collect(Collectors.toSet());
        workshopDtlVO.setSpeakers(speakerVOs);

        Set<RegistrationVO> registrationVOs = workshop.getRegistrations().stream()
                                                                            .map(Registration::getAttendeeTicketId)
                                                                            .map(service::retrieveAttendeeTicketFromService)
                                                                            .collect(Collectors.toSet());
        workshopDtlVO.setRegistrations(registrationVOs);
        return workshopDtlVO;
    }

    private Workshop dtoToEntity(WorkshopDTO dto) {
        Workshop workshop = new Workshop();
        BeanUtils.copyProperties(dto, workshop);
        return workshop;
    }
}
