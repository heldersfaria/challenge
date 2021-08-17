package br.com.dextra.potter.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.UUID.fromString;

public class UUIDValidator implements ConstraintValidator<UUID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if ("".equals(value)) {
                return false;
            }
            if (value != null && 36 != value.length()) {
                return false;
            }
            if (value != null) {
                fromString(value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
