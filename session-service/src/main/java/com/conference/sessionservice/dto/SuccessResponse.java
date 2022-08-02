package com.conference.sessionservice.dto;

import org.springframework.http.HttpStatus;

public class SuccessResponse<T> extends ResponseDTO {
    public T data;

    public SuccessResponse(HttpStatus statusCode, T data) {
        super(statusCode);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}