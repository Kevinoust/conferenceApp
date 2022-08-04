package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "workshop_speakers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "speaker_id")
    private Long speakerId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getSpeakerId(), speaker.getSpeakerId()) &&
                Objects.equals(getWorkshop(), speaker.getWorkshop());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
