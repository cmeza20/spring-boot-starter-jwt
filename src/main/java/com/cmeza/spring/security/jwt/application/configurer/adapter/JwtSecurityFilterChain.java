package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

public interface JwtSecurityFilterChain {

    default void globalConfigure(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    default void configureFilter(HttpSecurity http, PathType pathType) throws Exception {

    }
}
