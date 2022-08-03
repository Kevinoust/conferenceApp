package com.conference.attendeeticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeTicketSumVO {
    private Long attendeeTicketId;
    private Long attendeeId;
    private Long ticketPriceId;
    private Long discountCodeId;
    private Double netPrice;
}