package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeVO {
    private String ticketTypeCode;
    private String ticketTypeName;
    private String description;
    private Boolean includesWorkshop;
}