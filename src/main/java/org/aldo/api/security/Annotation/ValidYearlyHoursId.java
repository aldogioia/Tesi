package org.aldo.api.security.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.aldo.api.security.Validator.YearlyHoursIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearlyHoursIdValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYearlyHoursId {
    String message() default "Invalid YearlyHours Id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
