package com.conference.tagservice.service;

import com.conference.tagservice.dto.TagDTO;
import com.conference.tagservice.entity.Tag;
import com.conference.tagservice.exception.ResourceNotFoundException;
import com.conference.tagservice.repository.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired
    private TagRepository repository;

    @Transactional(readOnly = true)
    public List<TagDTO> getAllTags() {
        return repository.findAllByProjection();
    }

    @Transactional(readOnly = true)
    public Tag getTag(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag id=" + id));
    }

    public Tag createTag(Tag tag) {
        return repository.saveAndFlush(tag);
    }

    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = getTag(id);
        BeanUtils.copyProperties(tag, existingTag, "tagId");
        return repository.save(existingTag);
    }

    public void deleteTag(Long id) {
        getTag(id);
        repository.deleteById(id);
    }
}
