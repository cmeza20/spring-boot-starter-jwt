package com.cmeza.spring.security.jwt.application.handler;

import com.cmeza.spring.security.jwt.domain.usecase.ports.input.handlers.JwtHandlerUseCase;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtHandlerController extends AbstractController {

    private final JwtHandlerUseCase jwtHandlerUseCase;

    private JwtHandlerController(JwtHandlerUseCase jwtHandlerUseCase) {
        this.jwtHandlerUseCase = jwtHandlerUseCase;
    }

    public static JwtHandlerController getInstance(JwtHandlerUseCase jwtHandlerUseCase) {
        return new JwtHandlerController(jwtHandlerUseCase);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jwtHandlerUseCase.handleRequestInternal(request, response);
    }
}
