package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ticket_prices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_price_id")
    private Long ticketPriceId;

    @Column(name = "base_price")
    private Double basePrice;

    @ManyToOne
    @JoinColumn(name = "pricing_category_code")
    private PricingCategory pricingCategory;

    @ManyToOne
    @JoinColumn(name = "ticket_type_code")
    private TicketType ticketType;
}
