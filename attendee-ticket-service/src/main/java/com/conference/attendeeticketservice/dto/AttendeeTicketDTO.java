package com.conference.attendeeticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeTicketDTO {
    private Long attendeeTicketId;
    @NotNull(message = "attendeeId is mandatory!")
    private Long attendeeId;
    @NotNull(message = "ticketPriceId is mandatory!")
    private Long ticketPriceId;
    private Long discountCodeId;
    private Double netPrice;
}