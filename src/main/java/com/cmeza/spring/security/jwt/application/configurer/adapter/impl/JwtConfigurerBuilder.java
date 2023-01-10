package com.cmeza.spring.security.jwt.application.configurer.adapter.impl;

import com.cmeza.spring.security.jwt.application.configurer.adapter.*;
import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurerDetails;

import java.util.Objects;

public class JwtConfigurerBuilder implements JwtConfigurer, JwtConfigurerDetails {
    private final JwtAdvancedConfigurerDetails jwtAdvancedConfigurerDetails;
    private JwtUserDetailsService<?, ?> jwtUserDetailsService;
    private JwtSecurityExceptionHandling jwtSecurityExceptionHandling;
    private JwtSecurityFilterChain jwtSecurityFilterChain;
    private boolean configured;

    public JwtConfigurerBuilder(JwtAdvancedConfigurerDetails jwtAdvancedConfigurerDetails) {
        this.jwtAdvancedConfigurerDetails = jwtAdvancedConfigurerDetails;
    }

    @Override
    public <R, E> JwtConfigurer setJwtUserDetailsService(JwtUserDetailsService<R, E> jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.configured = true;
        return this;
    }

    @Override
    public JwtConfigurer setJwtSecurityExceptionHandling(JwtSecurityExceptionHandling jwtSecurityExceptionHandling) {
        this.jwtSecurityExceptionHandling = jwtSecurityExceptionHandling;
        return this;
    }

    @Override
    public JwtConfigurer setJwtSecurityFilterChain(JwtSecurityFilterChain jwtSecurityFilterChain) {
        this.jwtSecurityFilterChain = jwtSecurityFilterChain;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, E> JwtUserDetailsService<R, E> getJwtUserDetailsService() {
        return (JwtUserDetailsService<R, E>) jwtUserDetailsService;
    }

    @Override
    public JwtSecurityExceptionHandling getJwtSecurityExceptionHandling() {
        return jwtSecurityExceptionHandling;
    }

    @Override
    public JwtSecurityFilterChain getJwtSecurityFilterChain() {
        return jwtSecurityFilterChain;
    }

    @Override
    public JwtAdvancedConfigurerDetails getJwtAdvancedConfigurerDetails() {
        return jwtAdvancedConfigurerDetails;
    }

    @Override
    public boolean isConfigured() {
        return configured;
    }

    public void build() {
        if (Objects.isNull(jwtUserDetailsService)) {
            jwtUserDetailsService = new JwtUserDetailsServiceDefault();
        }
        if (Objects.isNull(jwtSecurityExceptionHandling)) {
            jwtSecurityExceptionHandling = new JwtSecurityExceptionHandlingDefault();
        }
        if (Objects.isNull(jwtSecurityFilterChain)) {
            jwtSecurityFilterChain = new JwtSecurityFilterChainDefault();
        }
    }

}
