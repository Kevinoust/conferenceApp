package com.conference.workshopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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