package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCodeVO {
    private Long discountCodeId;
    private String discountCode;
    private String discountName;
    private String discountType;
    private Double discountAmount;
}