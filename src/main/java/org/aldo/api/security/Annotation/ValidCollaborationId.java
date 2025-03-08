package org.aldo.api.security.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.aldo.api.security.Validator.CollaborationIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CollaborationIdValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCollaborationId {
    String message() default "Invalid Collaboration Id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
