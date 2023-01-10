package com.cmeza.spring.security.jwt.application.security;

import com.cmeza.spring.security.jwt.application.filters.SecuredAuthFilterConfigurer;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.configs.SecurityConfigUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.SecuredFilterUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@ConditionalOnProperty(prefix = JwtConstants.PROPERTY_PATHS_PREFIX, name = "secured.exposed", havingValue = "true", matchIfMissing = true)
public final class SecuredSecurityConfig extends AbstractSecurityConfig {

    private final JwtProperties jwtProperties;

    public SecuredSecurityConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.printLog();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChainSecured(HttpSecurity http, SecurityConfigUseCase securityConfigUseCase, SecuredFilterUseCase securedFilterUseCase) throws Exception {
        return securityConfigUseCase.bindSecurity(http, new SecuredAuthFilterConfigurer(securedFilterUseCase), getPathProperties(), PathType.SECURED);
    }

    @Override
    protected JwtPathProperties getPathProperties() {
        return jwtProperties.getPaths().getSecured();
    }

    @Override
    protected String logTitle() {
        return PathType.SECURED.name();
    }
}