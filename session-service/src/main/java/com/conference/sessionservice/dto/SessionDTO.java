package com.conference.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long sessionId;

    @NotNull(message = "sessionName is mandatory!")
    private String sessionName;

    @NotNull(message = "sessionDescription is mandatory!")
    private String sessionDescription;

    @NotNull(message = "sessionLength is mandatory!") @Positive(message = "sessionLength should be greater than 0!")
    private Integer sessionLength;
}
