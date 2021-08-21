package br.com.dextra.potter.dto;


import br.com.dextra.potter.utils.ConstraintAbstractTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ConstraintDTOsTest extends ConstraintAbstractTest {
    public static Stream<Arguments> sourceNotNull() {
        return Stream.of(Arguments.of(Pair.of(new CharacterDTO(), Map.of(NOT_NULL_KEY, List.of("name", "school", "patronus", "house")))));
    }
}