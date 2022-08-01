package com.conference.timeslotservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CheckTimeFormatValidator implements ConstraintValidator<CheckTimeFormat, String> {
    private String pattern;

    @Override
    public void initialize(CheckTimeFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return true;

        try {
            LocalTime.parse(s, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}

