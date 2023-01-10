package com.cmeza.spring.security.jwt.domain.usecase.ports.input.configs;

import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.AbstractController;

public interface SecurityConfigUseCase {
    SecurityFilterChain bindSecurity(HttpSecurity http, AbstractHttpConfigurer<?, HttpSecurity> httpConfigurer, JwtPathProperties pathProperties, PathType pathType) throws Exception;

    SimpleUrlHandlerMapping bindMapping(AbstractController abstractController, JwtPathProperties pathProperties);

    String hasAnyRole(String rolePrefix, String... authorities);
}
