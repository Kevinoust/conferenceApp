package com.conference.attendeeticketservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "discount_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_code_id")
    private Long discountCodeId;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_amount")
    private Double discountAmount;
}
