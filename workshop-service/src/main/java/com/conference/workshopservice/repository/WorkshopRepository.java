package com.conference.workshopservice.repository;

import com.conference.workshopservice.entity.Workshop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

import static com.conference.workshopservice.entity.Workshop.ENTITYGRAPH_ALL;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    @Override
    @EntityGraph(value = ENTITYGRAPH_ALL)
    Optional<Workshop> findById(Long aLong);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM workshops w WHERE workshopId = :id")
    Optional<Workshop> findByIdWithLock(Long id);
}
