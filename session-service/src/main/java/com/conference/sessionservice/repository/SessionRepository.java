package com.conference.sessionservice.repository;

import com.conference.sessionservice.entity.Session;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import static com.conference.sessionservice.entity.Session.ENTITYGRAPH_SESSION_SPEAKERS_TAGS_SCHEDULE;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionUUID(UUID uuid);

    @EntityGraph(value = ENTITYGRAPH_SESSION_SPEAKERS_TAGS_SCHEDULE)
    @Override
    Optional<Session> findById(Long aLong);
}
