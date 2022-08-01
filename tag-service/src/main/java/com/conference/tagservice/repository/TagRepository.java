package com.conference.tagservice.repository;

import com.conference.tagservice.dto.TagDTO;
import com.conference.tagservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT new com.conference.tagservice.dto.TagDTO(t.tagId, t.description) FROM tags t")
    List<TagDTO> findAllByProjection();
}
