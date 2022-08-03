package com.conference.workshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}