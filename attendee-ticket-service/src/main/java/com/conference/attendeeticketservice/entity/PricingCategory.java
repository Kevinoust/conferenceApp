package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "pricing_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingCategory {
    @Id
    @Column(name = "pricing_category_code")
    private String pricingCategoryCode;

    @Column(name = "pricing_category_name")
    private String pricingCategoryName;

    @Column(name = "pricing_start_date")
    private LocalDate pricingStartDate;

    @Column(name = "pricing_end_date")
    private LocalDate pricingEndDate;
}