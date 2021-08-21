package br.com.dextra.potter.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class ConstraintAbstractTest {

    protected static final String NOT_NULL_KEY = "javax.validation.constraints.NotNull.message";

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @ParameterizedTest
    @MethodSource("sourceNotNull")
    public <T> void testNotNull(Pair<T, Map<String, List<String>>> cenarios) {

        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(cenarios.getLeft());

        Optional<Map.Entry<String, List<String>>> optionalEntry = cenarios.getRight().entrySet().stream()
                .filter(e -> NOT_NULL_KEY.equals(e.getKey())).findAny();

        if (optionalEntry.isEmpty()) {
            fail("Test not found.");
        }

        violations.forEach(v ->
                assertTrue(() -> optionalEntry.get().getValue().stream().anyMatch(field ->
                                v.getPropertyPath().toString().equals(field) && v.getMessageTemplate().contains(optionalEntry.get().getKey())),
                        v.toString()));

    }
}
