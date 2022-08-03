package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingCategoryVO {
    private String pricingCategoryCode;
    private String pricingCategoryName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate pricingStartDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate pricingEndDate;
}