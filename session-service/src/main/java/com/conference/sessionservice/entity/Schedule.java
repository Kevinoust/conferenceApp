package com.conference.sessionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "session_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Column(name = "time_slot_id")
    private Long timeSlotId;

    @Column(name = "room")
    private String room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(getSession(), schedule.getSession()) &&
                Objects.equals(getTimeSlotId(), schedule.getTimeSlotId()) &&
                Objects.equals(getRoom(), schedule.getRoom());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}