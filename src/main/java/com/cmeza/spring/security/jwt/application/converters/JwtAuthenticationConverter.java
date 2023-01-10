package com.cmeza.spring.security.jwt.application.converters;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.domain.model.tokens.JwtAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtAuthenticationConverter implements AuthenticationConverter {

    private static final String WHITE_SPACE = " ";
    private final JwtPathProperties pathProperties;
    private final JwtSecurityConfigurer jwtSecurityConfigurer;

    public JwtAuthenticationConverter(JwtSecurityConfigurer jwtSecurityConfigurer, JwtPathProperties pathProperties) {
        this.jwtSecurityConfigurer = jwtSecurityConfigurer;
        this.pathProperties = pathProperties;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(header) || header.isEmpty()) {
            throw new JwtAuthenticationException("Authentication header required");
        }

        String token;
        String prefix = jwtSecurityConfigurer.getJwtProperties().getConfiguration().getTokenPrefix();
        String[] authElements = header.split(WHITE_SPACE);

        if (prefix.isEmpty()) {
            if (authElements.length != 1) {
                throw new JwtAuthenticationException("Token does not require prefix");
            }
            token = header;
        } else {
            if (!header.startsWith(prefix) || !prefix.equals(authElements[0])) {
                throw new JwtAuthenticationException("incorrect prefix(use " + prefix + ")");
            }
            if (authElements.length != 2) {
                throw new JwtAuthenticationException("The token is malformed");
            }

            token = authElements[1];
        }
        return new JwtAuthenticationToken(token, populateDefaultAuthorities());
    }

    private List<GrantedAuthority> populateDefaultAuthorities() {
        if (!jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().isConfigured() && Objects.nonNull(pathProperties.getRole()) && pathProperties.getRole().length > 0) {
            return Arrays.stream(pathProperties.getRole())
                    .map(r -> pathProperties.getRolePrefix() + r)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
