package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "attendee_tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendee_ticket_id")
    private Long attendeeTicketId;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne
    @JoinColumn(name = "ticket_price_id")
    private TicketPrice ticketPrice;

    @ManyToOne
    @JoinColumn(name = "discount_code_id")
    private DiscountCode discountCode;

    @Column(name = "net_price")
    private Double netPrice;
}
