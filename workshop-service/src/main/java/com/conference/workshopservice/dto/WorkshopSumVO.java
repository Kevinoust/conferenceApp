package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopSumVO {
    private Long workshopId;
    private String workshopName;
    private String description;
    private String requirements;
    private String room;
    private Integer capacity;
}
