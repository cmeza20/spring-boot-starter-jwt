package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurerDetails;

public interface JwtConfigurerDetails {

    <R, E> JwtUserDetailsService<R, E> getJwtUserDetailsService();

    JwtSecurityExceptionHandling getJwtSecurityExceptionHandling();

    JwtSecurityFilterChain getJwtSecurityFilterChain();

    JwtAdvancedConfigurerDetails getJwtAdvancedConfigurerDetails();

    boolean isConfigured();
}
