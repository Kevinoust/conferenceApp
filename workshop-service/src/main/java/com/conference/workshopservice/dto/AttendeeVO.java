package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeVO {
    private Long attendeeId;
    private String firstName;
    private String lastName;
    private String title;
    private String company;
    private String email;
    private String phone_number;
}
