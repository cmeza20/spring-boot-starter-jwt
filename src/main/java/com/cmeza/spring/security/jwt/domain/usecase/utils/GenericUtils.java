package com.cmeza.spring.security.jwt.domain.usecase.utils;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public final class GenericUtils {

    private GenericUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> Class<R> getResponseClass(JwtUserDetailsService<R, ?> jwtUserDetailsService) {
        return (Class<R>) extractGenericClass(jwtUserDetailsService, 0);
    }

    public static <E> Class<E> getEntityClass(JwtUserDetailsService<?, E> jwtUserDetailsService) {
        return (Class<E>) extractGenericClass(jwtUserDetailsService, 1);
    }

    private static Class<?> extractGenericClass(JwtUserDetailsService<?, ?> jwtUserDetailsService, int argument) {
        Type[] types = jwtUserDetailsService.getClass().getGenericInterfaces();
        return Arrays.stream(types).filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast)
                .map(t -> (Class<?>) t.getActualTypeArguments()[argument])
                .findAny().orElseThrow(() -> new JwtAuthenticationException("Type not found!"));
    }
}
