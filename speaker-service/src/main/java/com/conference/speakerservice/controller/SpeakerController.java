package com.conference.speakerservice.controller;

import com.conference.speakerservice.dto.ResponseDTO;
import com.conference.speakerservice.dto.SpeakerDTO;
import com.conference.speakerservice.dto.SuccessResponse;
import com.conference.speakerservice.entity.Speaker;
import com.conference.speakerservice.service.SpeakerService;
import com.conference.speakerservice.validator.InBetween;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/speakers")
public class SpeakerController {
    @Autowired
    private SpeakerService service;

    @GetMapping
    public ResponseDTO getAllSpeakers(@RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 0, message = "Query param page must >= 1") int page,
                                      @RequestParam(name = "size", required = false, defaultValue = "20") @Min(value = 1, message = "Query param size must >= 1") int size,
                                      @RequestParam(name = "sort", required = false, defaultValue = "speakerId") String sort,
                                      @RequestParam(name = "order", required = false, defaultValue = "asc") @InBetween(in = {"asc", "desc"}, message = "Query param order must be in between {in}") String order) {
        return new SuccessResponse<>(OK, service.getAllSpeakers(page, size, sort, order));
    }

    @GetMapping(value = "{id}")
    public ResponseDTO getSpeaker(@PathVariable Long id) {
        return new SuccessResponse<>(OK, service.getSpeaker(id));
    }

    @PostMapping
    @ResponseStatus(OK)
    public ResponseDTO createSpeaker(@Valid @RequestBody SpeakerDTO speakerDTO) {
        return new SuccessResponse<>(CREATED, service.createSpeaker(dtoToEntity(speakerDTO)));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseDTO deleteSpeaker(@PathVariable Long id) {
        service.deleteSpeaker(id);
        return new SuccessResponse<>(NO_CONTENT, "");
    }

    @PutMapping(value = "{id}")
    public ResponseDTO updateSpeaker(@PathVariable Long id, @Valid @RequestBody SpeakerDTO speakerDTO) {
        return new SuccessResponse<>(OK, service.updateSpeaker(id, dtoToEntity(speakerDTO)));
    }

    private Speaker dtoToEntity(SpeakerDTO dto) {
        Speaker speaker = new Speaker();
        BeanUtils.copyProperties(dto, speaker);
        return speaker;
    }
}
