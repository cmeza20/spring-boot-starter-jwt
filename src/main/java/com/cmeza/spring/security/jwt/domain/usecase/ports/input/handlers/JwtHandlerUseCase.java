package com.cmeza.spring.security.jwt.domain.usecase.ports.input.handlers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtHandlerUseCase {
    ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
