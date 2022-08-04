package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@NamedEntityGraph(
        name = Workshop.ENTITYGRAPH_ALL,
        attributeNodes = {
                @NamedAttributeNode("speakers"),
                @NamedAttributeNode("registrations")
        }
)
@Entity(name = "workshops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@OptimisticLocking(type = OptimisticLockType.DIRTY)                                                                     // to enable OptimisticLocking
@DynamicUpdate
public class Workshop {
    public static final String ENTITYGRAPH_ALL = "workshop_all";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workshop_id")
    private Long workshopId;

    @Column(name = "workshop_name")
    private String workshopName;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "room")
    private String room;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "workshop", cascade = ALL, orphanRemoval = true)
    private Set<Speaker> speakers;

    @OneToMany(mappedBy = "workshop", cascade = ALL, orphanRemoval = true)
    private Set<Registration> registrations;

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
        speaker.setWorkshop(this);
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
        registration.setWorkshop(this);
    }
}