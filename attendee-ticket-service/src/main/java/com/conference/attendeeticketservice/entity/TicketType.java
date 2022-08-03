package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ticket_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    @Id
    @Column(name = "ticket_type_code")
    private String ticketTypeCode;

    @Column(name = "ticket_type_name")
    private String ticketTypeName;

    @Column(name = "description")
    private String description;

    @Column(name = "includes_workshop")
    private Boolean includesWorkshop;
}
