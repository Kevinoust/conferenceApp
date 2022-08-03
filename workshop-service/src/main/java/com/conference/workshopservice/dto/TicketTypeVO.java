package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeVO {
    private String ticketTypeCode;
    private String ticketTypeName;
    private String description;
    private Boolean includesWorkshop;
}
