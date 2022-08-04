package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends ResponseDTO {
    private List<String> errorMessages;

    public ErrorResponse(HttpStatus errorStatusCode, String... errorMessages) {
        super(errorStatusCode);
        this.errorMessages = Arrays.asList(errorMessages);
    }
}