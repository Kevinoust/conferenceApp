package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPriceVO {
    private Long ticketPriceId;
    private TicketTypeVO ticketType;
    private PricingCategoryVO pricingCategory;
    private Double basePrice;
}
