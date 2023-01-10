package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.HandlerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public interface JwtSecurityExceptionHandling {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    default void globalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus httpStatus, HandlerType handlerType) throws IOException, ServletException {
        if (printException()) {
            exception.printStackTrace();
        }
        response.setStatus(httpStatus.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Object obj = exceptionHandler(handlerType, exception);
        if (Objects.isNull(obj)) {
            response.sendError(httpStatus.value(), exception.getMessage());
        } else {
            OBJECT_MAPPER.writeValue(response.getOutputStream(), obj);
        }
    }

    default void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus httpStatus) throws IOException, ServletException {
        globalExceptionHandler(request, response, exception, httpStatus, HandlerType.EXCEPTION);
    }

    default void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        globalExceptionHandler(request, response, accessDeniedException, HttpStatus.FORBIDDEN, HandlerType.ACCESS_DENIED);
    }

    default void entryPointHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        globalExceptionHandler(request, response, authException, HttpStatus.UNAUTHORIZED, HandlerType.ENTRY_POINT);
    }

    default Object exceptionHandler(HandlerType handlerType, Exception exception) {
        return null;
    }

    default boolean printException() {
        return true;
    }

}
