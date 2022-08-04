package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "workshop_registrations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "attendee_ticket_id")
    private Long attendeeTicketId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;
        Registration that = (Registration) o;
        return Objects.equals(getAttendeeTicketId(), that.getAttendeeTicketId()) &&
                Objects.equals(getWorkshop(), that.getWorkshop());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
