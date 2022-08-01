package com.conference.speakerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeakerDTO {
    private Long speakerId;

    @NotNull(message = "firstName is mandatory!")
    private String firstName;

    @NotNull(message = "lastName is mandatory!")
    private String lastName;

    @NotNull(message = "title is mandatory!")
    private String title;

    @NotNull(message = "company is mandatory!")
    private String company;

    @NotNull(message = "speakerBio is mandatory!")
    private String speakerBio;

    private byte[] speakerPhoto;
}