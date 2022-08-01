package com.conference.speakerservice.repository;

import com.conference.speakerservice.dto.SpeakerDTO;
import com.conference.speakerservice.entity.Speaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    @Query("SELECT new com.conference.speakerservice.dto.SpeakerDTO(s.speakerId, s.firstName, s.lastName, s.title, s.company, s.speakerBio, s.speakerPhoto) FROM speakers s")
    Page<SpeakerDTO> findAllByProjectionPaged(Pageable pageable);

    @Query("SELECT new com.conference.speakerservice.dto.SpeakerDTO(s.speakerId, s.firstName, s.lastName, s.title, s.company, s.speakerBio, s.speakerPhoto) FROM speakers s")
    List<SpeakerDTO> findAllByProjection();
}
