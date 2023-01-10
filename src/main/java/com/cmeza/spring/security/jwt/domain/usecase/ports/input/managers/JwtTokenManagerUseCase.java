package com.cmeza.spring.security.jwt.domain.usecase.ports.input.managers;

import com.cmeza.spring.security.jwt.domain.model.jwt.JwtBuilderWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface JwtTokenManagerUseCase {
    User createUser(Authentication authentication, List<GrantedAuthority> authorities);

    JwtBuilderWrapper createJwtBuilder(String subject);

    Claims decodeJwt(String token);
}
