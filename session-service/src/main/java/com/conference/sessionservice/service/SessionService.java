package com.conference.sessionservice.service;

import com.conference.sessionservice.dto.*;
import com.conference.sessionservice.entity.Schedule;
import com.conference.sessionservice.entity.Session;
import com.conference.sessionservice.entity.Speaker;
import com.conference.sessionservice.entity.Tag;
import com.conference.sessionservice.exception.ResourceNotFoundException;
import com.conference.sessionservice.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionService {
    @Autowired
    private SessionRepository repository;

    @Autowired
    private RestService restService;

    @Transactional(readOnly = true)
    public Page<Session> getAllSessions(int page, int size, String sort, String order) {
        Sort sorter = Sort.by(Sort.Direction.fromString(order), sort);
        PageRequest pageRequest = PageRequest.of(page - 1, size, sorter);                                        // page number starts from 0

        Page<Session> sessionsPage = repository.findAll(pageRequest);                                                   // 1. Pagination
//        List<Long> sessionIds = sessionsPage.get().map(Session::getSessionId).collect(Collectors.toList());

//        List<Long> speakerIds = repository.findSpeakerIds(sessionIds);                                                // 2. Join Fetch

//        return sessionsPage.map(this::entityToSumDTO);                                                                // 3. Mapped back to pagination result
        return sessionsPage;
    }

    @Transactional(readOnly = true)
    public Session getSession(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session id=" + id));
    }

    public Session createSession(Session session, UUID idempotencyKey) {
        Optional<Session> existingSession = repository.findBySessionUUID(idempotencyKey);

        if(existingSession.isPresent()) {                                                                               // if existingSession matched the UUID, return it
            session = existingSession.get();
        } else {                                                                                                        // else create a new session
            session.setSessionUUID(idempotencyKey);
            session = repository.save(session);
        }

        return session;
    }

    public void deleteSession(Long id) {
        getSession(id);
        repository.deleteById(id);
    }

    public Session updateSession(Long id, Session session) {
        Session existingSession = getSession(id);
        existingSession.setSessionLength(session.getSessionLength());
        existingSession.setSessionDescription(session.getSessionDescription());
        existingSession.setSessionName(session.getSessionName());

        return repository.save(existingSession);
    }

    public Session updateSessionSpeakers(Long id, Set<Long> speakerIDs) {
        Session existingSession = getSession(id);

        Set<Speaker> existingSessionSpeakers = existingSession.getSpeakers();

        Set<Speaker> expectedSessionSpeakers = speakerIDs.stream()
                                                            .peek(this::retrieveSpeakerDetailFromService)               // validate if is real speaker
                                                            .map(speakerID -> {
                                                                    Speaker speaker = new Speaker();
                                                                    speaker.setSpeakerId(speakerID);
                                                                    speaker.setSession(existingSession);
                                                                    return speaker;
                                                            })
                                                            .collect(Collectors.toSet());

        existingSessionSpeakers.removeIf(speaker -> !expectedSessionSpeakers.contains(speaker));                        // delete unexpected, but existed speakers
        existingSessionSpeakers.addAll(expectedSessionSpeakers);                                                        // add expected, but non-existed speakers

        existingSession.setSpeakers(existingSessionSpeakers);
        return repository.save(existingSession);
    }

    public Session updateSessionTags(Long id, Set<Long> tagIDs) {
        Session existingSession = getSession(id);

        Set<Tag> existingSessionTags = existingSession.getTags();

        Set<Tag> expectedSessionTags = tagIDs.stream()
                                                .peek(this::retrieveTagDetailFromService)                               // validate if is real tag
                                                .map(tagID -> {
                                                        Tag tag = new Tag();
                                                        tag.setTagId(tagID);
                                                        tag.setSession(existingSession);
                                                        return tag;
                                                })
                                                .collect(Collectors.toSet());

        existingSessionTags.removeIf(tag -> !expectedSessionTags.contains(tag));                                        // delete unexpected, but existed tags
        existingSessionTags.addAll(expectedSessionTags);                                                                // add expected, but non-existed tags

        existingSession.setTags(existingSessionTags);
        return repository.save(existingSession);
    }

    public Session updateSessionSchedules(Long id, Set<ScheduleDTO> scheduleDTOs) {
        Session existingSession = getSession(id);

        Set<Schedule> existingSessionSchedules = existingSession.getSchedules();

        scheduleDTOs.stream().map(ScheduleDTO::getTimeSlotId).forEach(this::retrieveScheduleDetailFromService);         // validate if is real timeSlot

        Set<Schedule> expectedSessionSchedules = scheduleDTOs.stream()
                                                                .map(scheduleDTO -> {
                                                                    Schedule schedule = new Schedule();
                                                                    schedule.setRoom(scheduleDTO.getRoom());
                                                                    schedule.setTimeSlotId(scheduleDTO.getTimeSlotId());
                                                                    schedule.setSession(existingSession);
                                                                    return schedule;
                                                                })
                                                                .collect(Collectors.toSet());

        existingSessionSchedules.removeIf(tag -> !expectedSessionSchedules.contains(tag));                              // delete unexpected, but existed tags
        existingSessionSchedules.addAll(expectedSessionSchedules);                                                      // add expected, but non-existed tags

        existingSession.setSchedules(existingSessionSchedules);
        return repository.save(existingSession);
    }

    public SpeakerVO retrieveSpeakerDetailFromService(Long speakerId) {
        return restService.getForObject("http://SPEAKER-SERVICE/speakers/{1}",
                                                new ParameterizedTypeReference<SuccessResponse<SpeakerVO>>() {},
                                                speakerId
                                        ).getData();
    }

    public TagVO retrieveTagDetailFromService(Long tagId) {
        return restService.getForObject("http://TAG-SERVICE/tags/{1}",
                                                new ParameterizedTypeReference<SuccessResponse<TagVO>>() {},
                                                tagId
                                        ).getData();
    }

    public ScheduleDtlVO retrieveScheduleDetailFromService(Long timeSlotId) {
        return restService.getForObject("http://TIMESLOT-SERVICE/timeslots/{1}",
                                                new ParameterizedTypeReference<SuccessResponse<ScheduleDtlVO>>() {},
                                                timeSlotId
                                        ).getData();
    }

    public Set<SpeakerVO> retrieveSpeakerDetailFromService(Set<Speaker> speakers) {
        return speakers.stream()
                        .map(Speaker::getSpeakerId)
                        .map(this::retrieveSpeakerDetailFromService)
                        .collect(Collectors.toSet());
    }

    public Set<TagVO> retrieveTagDetailFromService(Set<Tag> tags) {
        return tags.stream()
                    .map(Tag::getTagId)
                    .map(this::retrieveTagDetailFromService)
                    .collect(Collectors.toSet());
    }

    public Set<ScheduleDtlVO> retrieveScheduleDetailFromService(Set<Schedule> schedules) {
        return schedules.stream()
                        .map(schedule -> {
                            Long timeSlotId = schedule.getTimeSlotId();
                            ScheduleDtlVO scheduleDtlVO = retrieveScheduleDetailFromService(timeSlotId);
                            scheduleDtlVO.setRoom(schedule.getRoom());
                            return scheduleDtlVO;
                        })
                        .collect(Collectors.toSet());
    }
}