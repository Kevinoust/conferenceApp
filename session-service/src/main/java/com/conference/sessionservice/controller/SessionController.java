package com.conference.sessionservice.controller;

import com.conference.sessionservice.dto.*;
import com.conference.sessionservice.entity.Schedule;
import com.conference.sessionservice.entity.Session;
import com.conference.sessionservice.entity.Speaker;
import com.conference.sessionservice.entity.Tag;
import com.conference.sessionservice.service.SessionService;
import com.conference.sessionservice.validator.InBetween;
import com.conference.sessionservice.validator.IsUUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@Validated
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService service;

    @GetMapping
    public ResponseDTO getAllSessions(@RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1, message = "Query param page must >= 1") int page,
                                      @RequestParam(name = "size", required = false, defaultValue = "20") @Min(value = 1, message = "Query param size must >= 1") int size,
                                      @RequestParam(name = "sort", required = false, defaultValue = "sessionId") String sort,
                                      @RequestParam(name = "order", required = false, defaultValue = "asc") @InBetween(in = {"asc", "desc"}, message = "Query param order must be in between {in}") String order) {
        Page<Session> sessions = service.getAllSessions(page, size, sort, order);
        return new SuccessResponse<>(OK, sessions.map(this::sessionEntityToSumDTO));
    }

    @GetMapping("{id}")
    public ResponseDTO getSession(@PathVariable long id) {
        Session session = service.getSession(id);
        return new SuccessResponse<>(OK, sessionEntityToDtlDTO(session));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseDTO createSession(@RequestHeader("Idempotency-Key") @IsUUID(message = "idempotencyKey is not an valid UUID") String idempotencyKey,
                                     @RequestBody @Valid SessionDtlDTO sessionDtlDTO) {
        UUID idempotencyKeyUUID = UUID.fromString(idempotencyKey);

        Session session = sessionDtlDTOToEntity(sessionDtlDTO);

        session = service.createSession(session, idempotencyKeyUUID);

        return new SuccessResponse<>(CREATED, sessionEntityToDtlDTO(session));
    }

    @PutMapping("{id}")
    public ResponseDTO updateSession(@PathVariable Long id, @Valid @RequestBody SessionDtlDTO sessionDtlDTO) {
        Session session = service.updateSession(id, sessionDtlDTOToEntity(sessionDtlDTO));
        return new SuccessResponse<>(OK, sessionEntityToDtlDTO(session));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseDTO deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return new SuccessResponse<>(NO_CONTENT,"");
    }

//    @PutMapping("{id}/speakers")
//    public ResponseDTO updateSessionSpeakers(@PathVariable Long id, @RequestBody List<Long> speakerIDs) {
//        return new SuccessResponse<>(OK, service.updateSessionSpeakers(id, speakerIDs));
//    }
//
//    @PutMapping("{id}/tags")
//    public ResponseDTO updateSessionTags(@PathVariable Long id, @RequestBody List<Long> tags) {
//        return new SuccessResponse<>(OK, service.updateSessionTags(id, tags));
//    }
//
//    @PutMapping("{id}/schedule")
//    public ResponseDTO updateSessionSchedule(@PathVariable("id") Long id, @Valid @RequestBody List<SessionSchedule> sessionSchedules) {
//        return new SuccessResponse<>(OK, service.updateSessionSchedule(id, sessionSchedules));
//    }

    private SessionSumDTO sessionEntityToSumDTO(Session session) {
        SessionSumDTO sessionSumDTO = new SessionSumDTO();
        BeanUtils.copyProperties(session, sessionSumDTO);
        return sessionSumDTO;
    }

    private SessionDtlDTO sessionEntityToDtlDTO(Session session) {
        SessionDtlDTO sessionDtlDTO = new SessionDtlDTO();
        BeanUtils.copyProperties(session, sessionDtlDTO);

        sessionDtlDTO.setSpeakerIds(session.getSpeakers().stream().map(Speaker::getSpeakerId).collect(Collectors.toSet()));
        sessionDtlDTO.setTagIds(session.getTags().stream().map(Tag::getTagId).collect(Collectors.toSet()));
        sessionDtlDTO.setSchedules(session.getSchedules().stream().map(this::scheduleEntityToDTO).collect(Collectors.toSet()));

        return sessionDtlDTO;
    }

    private Session sessionDtlDTOToEntity(SessionDtlDTO sessionDtlDTO) {
        Session session = new Session();
        session.setSessionId(sessionDtlDTO.getSessionId());
        session.setSessionDescription(sessionDtlDTO.getSessionDescription());
        session.setSessionLength(sessionDtlDTO.getSessionLength());
        session.setSessionName(sessionDtlDTO.getSessionName());

        Set<Speaker> speakers = sessionDtlDTO.getSpeakerIds().stream().map(speakerId -> {
                                                                                Speaker speaker = new Speaker();
                                                                                speaker.setSpeakerId(speakerId);
                                                                                session.addSpeaker(speaker);
                                                                                return speaker;
                                                                            }).collect(Collectors.toSet());
        session.setSpeakers(speakers);

        Set<Tag> tags = sessionDtlDTO.getTagIds().stream().map(tagId -> {
                                                                    Tag tag = new Tag();
                                                                    tag.setTagId(tagId);
                                                                    session.addTag(tag);
                                                                    return tag;
                                                                }).collect(Collectors.toSet());
        session.setTags(tags);

        Set<Schedule> schedules = sessionDtlDTO.getSchedules().stream().map(
                                                                            scheduleDTO -> {
                                                                                Schedule schedule = new Schedule();
                                                                                schedule.setTimeSlotId(scheduleDTO.getTimeSlotId());
                                                                                schedule.setRoom(scheduleDTO.getRoom());
                                                                                session.addSchedule(schedule);
                                                                                return schedule;
                                                                            }).collect(Collectors.toSet());
        session.setSchedules(schedules);
        return session;
    }

    private ScheduleDTO scheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTimeSlotId(schedule.getTimeSlotId());
        scheduleDTO.setRoom(schedule.getRoom());
        return scheduleDTO;
    }
}
