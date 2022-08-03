package com.conference.workshopservice.repository;

import com.conference.workshopservice.entity.Workshop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.conference.workshopservice.entity.Workshop.ENTITYGRAPH_ALL;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    @Override
    @EntityGraph(value = ENTITYGRAPH_ALL)
    Optional<Workshop> findById(Long aLong);
}
