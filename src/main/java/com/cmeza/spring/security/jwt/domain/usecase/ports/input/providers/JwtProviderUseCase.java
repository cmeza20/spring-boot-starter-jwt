package com.cmeza.spring.security.jwt.domain.usecase.ports.input.providers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface JwtProviderUseCase {
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
