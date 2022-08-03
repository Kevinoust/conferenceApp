package com.conference.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDtlVO {
    private Long sessionId;

    @NotNull(message = "sessionName is mandatory!")
    private String sessionName;

    @NotNull(message = "sessionDescription is mandatory!")
    private String sessionDescription;

    @NotNull(message = "sessionLength is mandatory!") @Positive(message = "sessionLength should be greater than 0!")
    private Integer sessionLength;

    private Set<SpeakerVO> speakers;

    private Set<TagVO> tags;

    private Set<ScheduleDtlVO> schedules;
}
