package com.conference.attendeeticketservice.dto;

import com.conference.attendeeticketservice.entity.DiscountCodeVO;
import com.conference.attendeeticketservice.entity.TicketPriceVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeTicketDtlVO {
    private Long attendeeTicketId;
    private AttendeeVO attendee;
    private TicketPriceVO ticketPrice;
    private DiscountCodeVO discountCode;
    private Double netPrice;
}