package com.conference.sessionservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = InBetweenValidator.class)
@Documented
public @interface InBetween {

    String message() default "{not.in.between}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] in();
}