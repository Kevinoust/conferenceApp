package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeakerVO {
    private Long speakerId;
    private String firstName;
    private String lastName;
    private String title;
    private String company;
    private String speakerBio;
    private byte[] speakerPhoto;
}