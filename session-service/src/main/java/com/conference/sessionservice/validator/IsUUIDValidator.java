package com.conference.sessionservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUUIDValidator implements ConstraintValidator<IsUUID, String> {
    private static final String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @Override
    public void initialize(IsUUID constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(regex);
    }
}
