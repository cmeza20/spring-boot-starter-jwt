package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.handlers.JwtHandlerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers.JwtHandlerGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInteractor extends InitializingComponent implements JwtHandlerUseCase {

    private JwtHandlerGateway jwtHandlerGateway;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jwtHandlerGateway.handleRequestInternal(request, response);
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtHandlerGateway = applicationContext.getBean(JwtHandlerGateway.class);
    }
}
