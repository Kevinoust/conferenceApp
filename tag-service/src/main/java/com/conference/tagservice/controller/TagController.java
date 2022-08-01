package com.conference.tagservice.controller;

import com.conference.tagservice.dto.ResponseDTO;
import com.conference.tagservice.dto.SuccessResponse;
import com.conference.tagservice.entity.Tag;
import com.conference.tagservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService service;

    @GetMapping
    public ResponseDTO getAllTags() {
        return new SuccessResponse<>(OK, service.getAllTags());
    }

    @GetMapping("{id}")
    public ResponseDTO getTag(@PathVariable Long id) {
        return new SuccessResponse<>(OK, service.getTag(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseDTO createTag(@Valid @RequestBody Tag tag) {
        return new SuccessResponse<>(CREATED, service.createTag(tag));
    }

    @PutMapping("{id}")
    public ResponseDTO updateTag(@PathVariable Long id, @Valid @RequestBody Tag tag) {
        return new SuccessResponse<>(OK, service.updateTag(id, tag));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseDTO deleteTag(@PathVariable Long id) {
        service.deleteTag(id);
        return new SuccessResponse<>(NO_CONTENT, "");
    }
}
