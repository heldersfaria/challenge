package br.com.dextra.potter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = {UUIDValidator.class})
@Target({ElementType.FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface UUID {

    String message() default "Invalid UUID.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
