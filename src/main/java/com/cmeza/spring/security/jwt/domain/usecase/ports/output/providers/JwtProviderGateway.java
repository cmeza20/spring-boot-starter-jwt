package com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.model.tokens.JwtAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface JwtProviderGateway<R, E> {
    Authentication loginAuthenticate(LoginAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException;

    Authentication jwtAuthenticate(JwtAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException;
}
