package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.model.tokens.JwtAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.providers.JwtProviderUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers.JwtProviderGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("unchecked")
public class ProviderInteractor<R, E> extends InitializingComponent implements JwtProviderUseCase {

    private JwtProviderGateway<R, E> jwtProviderGateway;
    private JwtUserDetailsService<R, E> jwtUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof LoginAuthenticationToken) {
            return jwtProviderGateway.loginAuthenticate((LoginAuthenticationToken) authentication, jwtUserDetailsService);
        } else if (authentication instanceof JwtAuthenticationToken) {
            return jwtProviderGateway.jwtAuthenticate((JwtAuthenticationToken) authentication, jwtUserDetailsService);
        }
        return null;
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtProviderGateway = applicationContext.getBean(JwtProviderGateway.class);
        this.jwtUserDetailsService = applicationContext.getBean(JwtUserDetailsService.class);
    }
}
