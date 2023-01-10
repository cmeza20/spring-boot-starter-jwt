package com.cmeza.spring.security.jwt.adapters.filters;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsServiceEvent;
import com.cmeza.spring.security.jwt.application.converters.LoginAuthenticationConverter;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.providers.JwtProviderUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.LoginFilterGateway;
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
import javax.validation.ConstraintViolationException;
import java.io.IOException;

public class LoginFilterAdapter<R> extends InitializingComponent implements LoginFilterGateway {

    private LoginAuthenticationConverter<R> loginAuthenticationConverter;
    private JwtProviderUseCase jwtProviderUseCase;

    @Override
    public void doFilterInternalLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain, RequestMatcher requestMatcher, JwtSecurityExceptionHandling jwtSecurityExceptionHandling, JwtUserDetailsServiceEvent jwtUserDetailsServiceEvent) throws ServletException, IOException {
        try {
            Authentication authentication = jwtProviderUseCase.authenticate(loginAuthenticationConverter.convert(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwtUserDetailsServiceEvent.onSuccessfulAuthentication(httpServletRequest, httpServletResponse, authentication, PathType.TOKEN);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ConstraintViolationException e) {
            SecurityContextHolder.clearContext();
            jwtSecurityExceptionHandling.exceptionHandler(httpServletRequest, httpServletResponse, new JwtAuthenticationException(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            jwtSecurityExceptionHandling.entryPointHandler(httpServletRequest, httpServletResponse, e);
            jwtUserDetailsServiceEvent.onUnsuccessfulAuthentication(httpServletRequest, httpServletResponse, e, PathType.TOKEN);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            jwtSecurityExceptionHandling.exceptionHandler(httpServletRequest, httpServletResponse, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtProviderUseCase = applicationContext.getBean(JwtProviderUseCase.class);
        this.loginAuthenticationConverter = new LoginAuthenticationConverter<>(jwtSecurityConfigurer, applicationContext.getBean(JwtUserDetailsService.class));
    }
}
