package com.conference.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long timeSlotId;
    @NotNull(message = "room is mandatory!")
    private String room;
}
