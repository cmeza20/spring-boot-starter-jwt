package com.cmeza.spring.security.jwt.application.security;

import com.cmeza.spring.security.jwt.application.filters.LoginAuthFilterConfigurer;
import com.cmeza.spring.security.jwt.application.handler.JwtHandlerController;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.configs.SecurityConfigUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.LoginFilterUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.handlers.JwtHandlerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@ConditionalOnProperty(prefix = JwtConstants.PROPERTY_PATHS_PREFIX, name = "token.exposed", havingValue = "true", matchIfMissing = true)
public final class TokenSecurityConfig extends AbstractSecurityConfig {

    private final SecurityConfigUseCase securityConfigUseCase;
    private final JwtProperties jwtProperties;

    public TokenSecurityConfig(SecurityConfigUseCase securityConfigUseCase, JwtProperties jwtProperties) {
        this.securityConfigUseCase = securityConfigUseCase;
        this.jwtProperties = jwtProperties;
        this.printLog();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChainToken(HttpSecurity http, LoginFilterUseCase loginFilterUseCase) throws Exception {
        return securityConfigUseCase.bindSecurity(http, new LoginAuthFilterConfigurer(loginFilterUseCase), getPathProperties(), PathType.TOKEN);
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMappingToken(JwtHandlerUseCase jwtHandlerUseCase) {
        return securityConfigUseCase.bindMapping(JwtHandlerController.getInstance(jwtHandlerUseCase), getPathProperties());
    }

    @Override
    protected JwtPathProperties getPathProperties() {
        return jwtProperties.getPaths().getToken();
    }

    @Override
    protected String logTitle() {
        return PathType.TOKEN.name();
    }
}