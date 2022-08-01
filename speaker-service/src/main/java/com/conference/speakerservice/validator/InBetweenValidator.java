package com.conference.speakerservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class InBetweenValidator implements ConstraintValidator<InBetween, String> {
    private String[] in;

    @Override
    public void initialize(InBetween constraintAnnotation) {
        this.in = constraintAnnotation.in();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return false;

        return Arrays.asList(in).contains(s);
    }
}
