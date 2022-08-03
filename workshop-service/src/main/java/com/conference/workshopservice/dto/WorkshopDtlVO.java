package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDtlVO {
    private Long workshopId;
    private String workshopName;
    private String description;
    private String requirements;
    private String room;
    private Integer capacity;
    private List<SpeakerVO> speakers;
    private List<RegistrationVO> registrations;
}
