package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "workshop_speakers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speaker {
    @Id
    @Column(name = "workshop_id")
    private Long workshopId;

    @Column(name = "speaker_id")
    private Long speakerId;

    @ManyToOne
    @JoinColumn(name = "workshop_id", insertable = false, updatable = false)
    private Workshop workshop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getWorkshopId(), speaker.getWorkshopId()) &&
                Objects.equals(getSpeakerId(), speaker.getSpeakerId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
