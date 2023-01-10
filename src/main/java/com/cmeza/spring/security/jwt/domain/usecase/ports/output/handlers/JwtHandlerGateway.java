package com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtHandlerGateway {
    ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
