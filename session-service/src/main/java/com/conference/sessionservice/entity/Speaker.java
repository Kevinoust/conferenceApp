package com.conference.sessionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "session_speakers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Column(name = "speaker_id")
    private Long speakerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speaker)) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getSession(), speaker.getSession()) &&
                Objects.equals(getSpeakerId(), speaker.getSpeakerId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
