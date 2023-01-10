package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.configs.SecurityConfigUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.configs.SecurityConfigGateway;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.AbstractController;

public class SecurityConfigInteractor extends InitializingComponent implements SecurityConfigUseCase {

    private SecurityConfigGateway securityConfigGateway;

    @Override
    public SecurityFilterChain bindSecurity(HttpSecurity http, AbstractHttpConfigurer<?, HttpSecurity> httpConfigurer, JwtPathProperties pathProperties, PathType pathType) throws Exception {
        return securityConfigGateway.bindSecurity(http, httpConfigurer, pathProperties, pathType);
    }

    @Override
    public SimpleUrlHandlerMapping bindMapping(AbstractController abstractController, JwtPathProperties pathProperties) {
        return securityConfigGateway.bindMapping(abstractController, pathProperties);
    }

    @Override
    public String hasAnyRole(String rolePrefix, String... authorities) {
        return securityConfigGateway.hasAnyRole(rolePrefix, authorities);
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.securityConfigGateway = applicationContext.getBean(SecurityConfigGateway.class);
    }
}
