package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface JwtUserDetailsServiceEvent {
    default void onSuccessfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication, PathType pathType) {
    }

    default void onUnsuccessfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException, PathType pathType) {
    }
}
