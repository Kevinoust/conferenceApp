package com.conference.attendeeticketservice.dto;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse extends ResponseDTO {
    private List<String> errorMessages;

    public ErrorResponse(HttpStatus errorStatusCode, String... errorMessages) {
        super(errorStatusCode);
        this.errorMessages = Arrays.asList(errorMessages);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}