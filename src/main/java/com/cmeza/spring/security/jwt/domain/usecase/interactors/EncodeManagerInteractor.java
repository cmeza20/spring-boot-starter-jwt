package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.managers.JwtEncodeManagerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtEncodeManagerGateway;
import org.springframework.context.ApplicationContext;

public class EncodeManagerInteractor extends InitializingComponent implements JwtEncodeManagerUseCase {

    private JwtEncodeManagerGateway jwtEncodeManagerGateway;

    @Override
    public String encode(String token) {
        return jwtEncodeManagerGateway.encode(token);
    }

    @Override
    public String decode(String token) {
        return jwtEncodeManagerGateway.decode(token);
    }

    @Override
    public int order() {
        return jwtEncodeManagerGateway.order();
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtEncodeManagerGateway = applicationContext.getBean(JwtEncodeManagerGateway.class);
    }
}
