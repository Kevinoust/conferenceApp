package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDTO {
    @NotNull(message = "workshopName is mandatory!")
    private String workshopName;
    @NotNull(message = "description is mandatory!")
    private String description;
    @NotNull(message = "requirements is mandatory!")
    private String requirements;
    @NotNull(message = "room is mandatory!")
    private String room;
    @NotNull(message = "capacity is mandatory!") @PositiveOrZero(message = "capacity must be >=0!")
    private Integer capacity;
}