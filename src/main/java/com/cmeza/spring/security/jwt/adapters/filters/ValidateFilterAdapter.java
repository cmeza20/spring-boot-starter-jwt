package com.cmeza.spring.security.jwt.adapters.filters;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsServiceEvent;
import com.cmeza.spring.security.jwt.application.converters.JwtAuthenticationConverter;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.providers.JwtProviderUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.ValidateFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateFilterAdapter extends InitializingComponent implements ValidateFilterGateway {
    private JwtAuthenticationConverter jwtAuthenticationConverter;
    private JwtProviderUseCase jwtProviderUseCase;

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtProviderUseCase = applicationContext.getBean(JwtProviderUseCase.class);
        this.jwtAuthenticationConverter = new JwtAuthenticationConverter(jwtSecurityConfigurer, jwtSecurityConfigurer.getJwtProperties().getPaths().getValidate());
    }

    @Override
    public void doFilterInternalValidate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain, RequestMatcher requestMatcher, JwtSecurityExceptionHandling jwtSecurityExceptionHandling, JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException {
        try {
            Authentication authentication = jwtProviderUseCase.authenticate(jwtAuthenticationConverter.convert(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtUserDetailsServiceEvent.onSuccessfulAuthentication(httpServletRequest, httpServletResponse, authentication, PathType.VALIDATE);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            jwtSecurityExceptionHandling.entryPointHandler(httpServletRequest, httpServletResponse, e);
            jwtUserDetailsServiceEvent.onUnsuccessfulAuthentication(httpServletRequest, httpServletResponse, e, PathType.VALIDATE);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            jwtSecurityExceptionHandling.exceptionHandler(httpServletRequest, httpServletResponse, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
