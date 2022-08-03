package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "workshop_registrations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @Column(name = "workshop_id")
    private Long workshopId;

    @Column(name = "attendee_ticket_id")
    private Long attendeeTicketId;

    @ManyToOne
    @JoinColumn(name = "workshop_id", insertable = false, updatable = false)
    private Workshop workshop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;
        Registration that = (Registration) o;
        return Objects.equals(getWorkshopId(), that.getWorkshopId()) &&
                Objects.equals(getAttendeeTicketId(), that.getAttendeeTicketId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
