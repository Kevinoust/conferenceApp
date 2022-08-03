package com.conference.sessionservice.controller;

import com.conference.sessionservice.dto.*;
import com.conference.sessionservice.entity.Session;
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

import static org.springframework.http.HttpStatus.*;

@RestController
@Validated
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService service;

    @GetMapping
    public SuccessResponse<?> getAllSessions(@RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1, message = "Query param page must >= 1") int page,
                                              @RequestParam(name = "size", required = false, defaultValue = "20") @Min(value = 1, message = "Query param size must >= 1") int size,
                                              @RequestParam(name = "sort", required = false, defaultValue = "sessionId") String sort,
                                              @RequestParam(name = "order", required = false, defaultValue = "asc") @InBetween(in = {"asc", "desc"}, message = "Query param order must be in between {in}") String order) {
        Page<Session> sessions = service.getAllSessions(page, size, sort, order);
        return new SuccessResponse<>(OK, sessions.map(this::entityToSumVO));
    }

    @GetMapping("{id}")
    public SuccessResponse<?> getSession(@PathVariable long id) {
        Session session = service.getSession(id);
        return new SuccessResponse<>(OK, entityToDtlVO(session));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public SuccessResponse<?> createSession(@RequestHeader("Idempotency-Key") @IsUUID(message = "idempotencyKey is not an valid UUID") String idempotencyKey,
                                     @RequestBody @Valid SessionDTO sessionDTO) {
        UUID idempotencyKeyUUID = UUID.fromString(idempotencyKey);

        Session session = dtoTOEntity(sessionDTO);

        session = service.createSession(session, idempotencyKeyUUID);

        return new SuccessResponse<>(CREATED, entityToDtlVO(session));
    }

    @PutMapping("{id}")
    public SuccessResponse<?> updateSession(@PathVariable Long id, @Valid @RequestBody SessionDTO sessionDTO) {
        Session session = service.updateSession(id, dtoTOEntity(sessionDTO));
        return new SuccessResponse<>(OK, entityToDtlVO(session));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public SuccessResponse<?> deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return new SuccessResponse<>(NO_CONTENT,"");
    }

    @PatchMapping("{id}/speakers")
    public SuccessResponse<?> updateSessionSpeakers(@PathVariable Long id, @RequestBody Set<Long> speakerIDs) {
        Session session = service.updateSessionSpeakers(id, speakerIDs);
        return new SuccessResponse<>(OK, entityToDtlVO(session));
    }

    @PatchMapping("{id}/tags")
    public SuccessResponse<?> updateSessionTags(@PathVariable Long id, @RequestBody Set<Long> tagIDs) {
        Session session = service.updateSessionTags(id, tagIDs);
        return new SuccessResponse<>(OK, entityToDtlVO(session));
    }

    @PatchMapping("{id}/schedules")
    public SuccessResponse<?> updateSessionSchedules(@PathVariable("id") Long id, @Valid @RequestBody Set<ScheduleDTO> scheduleDTOs) {
        Session session = service.updateSessionSchedules(id, scheduleDTOs);
        return new SuccessResponse<>(OK, entityToDtlVO(session));
    }

    private Session dtoTOEntity(SessionDTO sessionDTO) {
        Session session = new Session();
        BeanUtils.copyProperties(sessionDTO, session);
        return session;
    }

    private SessionSumVO entityToSumVO(Session session) {
        SessionSumVO sessionSumVO = new SessionSumVO();
        BeanUtils.copyProperties(session, sessionSumVO);
        return sessionSumVO;
    }

    private SessionDtlVO entityToDtlVO(Session session) {
        SessionDtlVO sessionDtlVO = new SessionDtlVO();
        BeanUtils.copyProperties(session, sessionDtlVO);

        sessionDtlVO.setSpeakers(service.retrieveSpeakerDetailFromService(session.getSpeakers()));
        sessionDtlVO.setTags(service.retrieveTagDetailFromService(session.getTags()));
        sessionDtlVO.setSchedules(service.retrieveScheduleDetailFromService(session.getSchedules()));

        return sessionDtlVO;
    }
}
