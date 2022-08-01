package com.conference.timeslotservice.dto;

import com.conference.timeslotservice.validator.CheckDateFormat;
import com.conference.timeslotservice.validator.CheckTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeslotDTO {
    private Long timeSlotId;

    @NotBlank(message = "timeSlotDate is mandatory!")
    @CheckDateFormat(pattern = "yyyy-MM-dd", message = "timeSlotDate must be yyyy-MM-dd")
    private String timeSlotDate;

    @NotBlank(message = "startTime is mandatory!")
    @CheckTimeFormat(pattern = "HH:mm:ss", message = "startTime must be HH:mm:ss")
    private String startTime;

    @NotBlank(message = "endTime is mandatory!")
    @CheckTimeFormat(pattern = "HH:mm:ss", message = "endTime must be HH:mm:ss")
    private String endTime;

    @NotNull(message = "isKeynoteTimeSlot is mandatory!")
    private Boolean isKeynoteTimeSlot;

    // for DTO projection
    public TimeslotDTO(Long timeSlotId, LocalDate timeSlotDate, LocalTime startTime, LocalTime endTime, Boolean isKeynoteTimeSlot) {
        this.timeSlotId = timeSlotId;
        this.timeSlotDate = timeSlotDate.format(DateTimeFormatter.ISO_DATE);
        this.startTime = startTime.format(DateTimeFormatter.ISO_TIME);
        this.endTime = endTime.format(DateTimeFormatter.ISO_TIME);
        this.isKeynoteTimeSlot = isKeynoteTimeSlot;
    }
}