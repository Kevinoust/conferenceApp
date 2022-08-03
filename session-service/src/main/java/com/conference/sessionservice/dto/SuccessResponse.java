package com.conference.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> extends ResponseDTO {
    public T data;

    public SuccessResponse(HttpStatus statusCode, T data) {
        super(statusCode);
        this.data = data;
    }
}