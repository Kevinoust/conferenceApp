package com.conference.timeslotservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for validate Date format
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckDateFormatValidator.class)
@Documented
public @interface CheckDateFormat {

    String message() default "{message.key}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String pattern();
}