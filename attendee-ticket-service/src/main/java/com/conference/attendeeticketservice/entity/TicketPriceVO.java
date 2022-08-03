package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPriceVO {
    private Long ticketPriceId;
    private PricingCategoryVO pricingCategory;
    private TicketTypeVO ticketType;
    private Double basePrice;
}
