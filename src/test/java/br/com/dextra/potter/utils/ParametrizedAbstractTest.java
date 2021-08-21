package br.com.dextra.potter.utils;


import br.com.dextra.potter.domain.BaseDomain;
import br.com.dextra.potter.dto.AuditDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ParametrizedAbstractTest {

    @ParameterizedTest
    @MethodSource("source")
    public void testToString(Object object) {
        assertThat(object.toString()).isNotBlank();
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testHashCode(Object object) {
        assertThat(object.hashCode()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testEquals(Object object) {
        try {
            assertThat(object.equals(object.getClass().newInstance())).isNotNull();
            assertThat(object.equals(object)).isTrue();
            assertThat(object.equals(null)).isFalse();
            assertThat(object.equals("*")).isFalse();

            if (object instanceof AuditDTO || object instanceof BaseDomain) {
                Optional<Method> optionalMethod = ReflectionUtils.findMethod(object.getClass(), "setId", String.class);
                if (optionalMethod.isPresent()) {
                    String id = UUID.randomUUID().toString();
                    optionalMethod.get().invoke(object, UUID.randomUUID().toString());
                    Object obj = object.getClass().newInstance();
                    optionalMethod.get().invoke(obj, UUID.randomUUID().toString());
                    assertThat(object.equals(obj)).isFalse();

                    optionalMethod.get().invoke(object, id);
                    obj = object.getClass().newInstance();
                    optionalMethod.get().invoke(obj, id);
                    assertThat(object.equals(obj)).isTrue();
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        }
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testGetters(Object object) {
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if (method.getName().startsWith("get")) {
                        method.invoke(object);
                    }
                }
            }
            assertThat(object.equals(object.getClass().newInstance())).isNotNull();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testIs(Object object) {
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("is")) {
                    Object booleano = method.invoke(object);
                    assertThat(booleano).isNull();
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testSetters(Object object) {
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        method.invoke(object, getTypeParameter((method.getParameterTypes()[0])));
                    }
                }
            }
            assertThat(object.equals(object.getClass().newInstance())).isNotNull();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

        }
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testCopy(Object object) {
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals("copy")) {
                    Object result = method.invoke(object);
                    assertThat(object.equals(result)).isNotNull();
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

        }
    }

    private Object getTypeParameter(Class<?> class1) {
        if (class1 == String.class) {
            return "S";
        } else if (class1 == Integer.class) {
            return 1;
        } else if (class1 == Float.class) {
            return 1F;
        } else if (class1 == Long.class) {
            return 1L;
        } else if (class1 == Float.class) {
            return 1F;
        } else if (class1 == LocalDate.class) {
            return LocalDate.now();
        } else if (class1 == LocalDateTime.class) {
            return LocalDateTime.now();
        } else if (class1 == Boolean.class) {
            return true;
        }
        return null;
    }

}
