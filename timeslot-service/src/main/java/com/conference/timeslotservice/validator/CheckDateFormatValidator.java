package com.conference.timeslotservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckDateFormatValidator implements ConstraintValidator<CheckDateFormat, String> {
    private String pattern;

    @Override
    public void initialize(CheckDateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return true;

        try {
            LocalDate.parse(s, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}