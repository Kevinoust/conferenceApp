package com.conference.sessionservice.service;

import com.conference.sessionservice.entity.Session;
import com.conference.sessionservice.exception.ResourceNotFoundException;
import com.conference.sessionservice.repository.ScheduleRepository;
import com.conference.sessionservice.repository.SessionRepository;
import com.conference.sessionservice.repository.SpeakerRepository;
import com.conference.sessionservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SessionService {
    @Autowired
    private SessionRepository repository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

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

//    public Session updateSessionSpeakers(Long id, List<Long> speakerIDs) {
//        Session existingSession = getSession(id);
//
//        Set<Speaker> newSpeakers = speakerIDs.stream().map(speakerService::getSpeaker).collect(Collectors.toSet());     // validation on every speakers
//        existingSession.setSpeakers(newSpeakers);
//
//        return repository.save(existingSession);
//    }
//
//    public Session updateSessionTags(Long id, List<Long> tagIDs) {
//        Session existingSession = getSession(id);
//        // validation on every tags, return 404 if tag is not found
//        Set<Tag> newTags = tagIDs.stream().map(tagService::getTag).collect(Collectors.toSet());
//
//        existingSession.setTags(newTags);
//        return repository.save(existingSession);
//    }
//
//    public Session updateSessionSchedule(Long id, List<SessionSchedule> sessionSchedules) {
//        Session existingSession = getSession(id);
//
//        Set<SessionSchedule> newSessionSchedules = sessionSchedules.stream().peek(sessionSchedule -> {
//            sessionSchedule.setSession(existingSession);
//            sessionSchedule.setTimeslot(timeslotsService.getTimeSlot(sessionSchedule.getTimeslot().getTimeSlotId()));
//        }).collect(Collectors.toSet());
//
//        existingSession.getSchedules().clear();
//        existingSession.getSchedules().addAll(newSessionSchedules);
//        return repository.save(existingSession);
//    }
}
