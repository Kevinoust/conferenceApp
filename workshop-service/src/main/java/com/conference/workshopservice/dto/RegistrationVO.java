package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationVO {
    private Long attendeeTicketId;
    private AttendeeVO attendee;
    private TicketPriceVO ticketPrice;
    private DiscountCodeVO discountCode;
    private Double netPrice;
}
