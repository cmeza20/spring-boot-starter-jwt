package com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsServiceEvent;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ValidateFilterGateway {

    void doFilterInternalValidate(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain,
            RequestMatcher requestMatcher,
            JwtSecurityExceptionHandling jwtSecurityExceptionHandling,
            JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException;

}
