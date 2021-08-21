package br.com.dextra.potter.domain;


import br.com.dextra.potter.utils.ParametrizedAbstractTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.platform.commons.util.ReflectionUtils;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isAbstract;

public class DomainTest extends ParametrizedAbstractTest {

    private static Stream<Arguments> source() {

        Package packageObject = BaseDomain.class.getPackage();
        String packageName = packageObject.getName();

        List<Class<?>> lista = ReflectionUtils.findAllClassesInPackage(packageName,
                c -> c.getPackage() == packageObject && !isAbstract(c.getModifiers()),
                s -> !s.endsWith("Test"));

        return lista.stream().map(c -> {
            try {
                return Arguments.of(c.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
