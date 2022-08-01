package com.conference.speakerservice.service;

import com.conference.speakerservice.dto.SpeakerDTO;
import com.conference.speakerservice.entity.Speaker;
import com.conference.speakerservice.exception.ResourceNotFoundException;
import com.conference.speakerservice.repository.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpeakerService {
    @Autowired
    private SpeakerRepository repository;

    @Transactional(readOnly = true)
    public Page<SpeakerDTO> getAllSpeakers(int page, int size, String sort, String order) {
        Sort sorter = Sort.by(Sort.Direction.fromString(order), sort);
        PageRequest pageRequest = PageRequest.of(page - 1, size, sorter);

        return repository.findAllByProjectionPaged(pageRequest);
    }

    @Transactional(readOnly = true)
    public List<SpeakerDTO> getAllSpeakers() {
        return repository.findAllByProjection();
    }

    @Transactional(readOnly = true)
    public Speaker getSpeaker(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Speaker id=" + id));
    }

    public Speaker createSpeaker(Speaker speaker) {
        return repository.save(speaker);
    }

    public void deleteSpeaker(Long id) {
        getSpeaker(id);
        repository.deleteById(id);
    }

    public Speaker updateSpeaker(Long id, Speaker speaker) {
        Speaker existingSpeaker = getSpeaker(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speakerId");
        return repository.save(existingSpeaker);
    }
}
