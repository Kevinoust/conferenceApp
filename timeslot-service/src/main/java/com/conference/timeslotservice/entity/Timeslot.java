package com.conference.timeslotservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "time_slots")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private Long timeSlotId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "time_slot_date")
    private LocalDate timeSlotDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "start_time")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_keynote_time_slot")
    private Boolean isKeynoteTimeSlot;
}