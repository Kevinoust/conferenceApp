package com.conference.sessionservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Entity(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = Session.ENTITYGRAPH_SESSION_SPEAKERS_TAGS_SCHEDULE,
        attributeNodes = {
                @NamedAttributeNode("speakers"),
                @NamedAttributeNode("tags"),
                @NamedAttributeNode("schedules"),
        }
)
public class Session {
    public static final String ENTITYGRAPH_SESSION_SPEAKERS_TAGS_SCHEDULE = "session-speakers-tags-schedule";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;
    @Column(name = "session_name")
    private String sessionName;
    @Column(name = "session_description")
    private String sessionDescription;
    @Column(name = "session_length")
    private Integer sessionLength;
    @Column(name = "session_uuid")
    @JsonIgnore
    private UUID sessionUUID;

    @OneToMany(mappedBy = "session", cascade = {PERSIST, MERGE, REFRESH, REMOVE})
    private Set<Speaker> speakers = new HashSet<>();

    @OneToMany(mappedBy = "session", cascade = {PERSIST, MERGE, REFRESH, REMOVE})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "session", cascade = {PERSIST, MERGE, REFRESH, REMOVE})
    private Set<Schedule> schedules = new HashSet<>();

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
        speaker.setSession(this);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setSession(this);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setSession(this);
    }
}
