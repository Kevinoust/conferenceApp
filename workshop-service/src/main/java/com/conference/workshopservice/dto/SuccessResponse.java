package com.conference.workshopservice.dto;

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