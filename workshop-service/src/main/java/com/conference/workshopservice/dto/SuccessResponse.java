package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> extends ResponseDTO {
    public T data;

    public SuccessResponse(HttpStatus statusCode, T data) {
        super(statusCode);
        this.data = data;
    }
}