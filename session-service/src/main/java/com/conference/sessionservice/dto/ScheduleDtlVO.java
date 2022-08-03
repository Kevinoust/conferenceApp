package com.conference.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDtlVO {
    private Long timeSlotId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate timeSlotDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;
    private Boolean isKeynoteTimeSlot;
    private String room;
}
