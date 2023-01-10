package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.LoginFilterUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.SecuredFilterUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.ValidateFilterUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.LoginFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.SecuredFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.ValidateFilterGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterInteractor<R, E> extends InitializingComponent implements ValidateFilterUseCase, LoginFilterUseCase, SecuredFilterUseCase {

    private final Logger log = LoggerFactory.getLogger(FilterInteractor.class);
    private ValidateFilterGateway validateFilterGateway;
    private LoginFilterGateway loginFilterGateway;
    private SecuredFilterGateway securedFilterGateway;
    private RequestMatcher validateRequestMatcher;
    private RequestMatcher loginRequestMatcher;
    private RequestMatcher securedRequestMatcher;
    private JwtSecurityExceptionHandling jwtSecurityExceptionHandling;
    private JwtUserDetailsService<R, E> jwtUserDetailsService;

    @Override
    public void doFilterInternalValidate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (this.notRequiresAuthentication(httpServletRequest, validateRequestMatcher)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        validateFilterGateway.doFilterInternalValidate(httpServletRequest, httpServletResponse, filterChain, validateRequestMatcher, jwtSecurityExceptionHandling, jwtUserDetailsService);
    }

    @Override
    public void doFilterInternalLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (this.notRequiresAuthentication(httpServletRequest, loginRequestMatcher)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        loginFilterGateway.doFilterInternalLogin(httpServletRequest, httpServletResponse, filterChain, loginRequestMatcher, jwtSecurityExceptionHandling, jwtUserDetailsService);
    }

    @Override
    public void doFilterInternalSecured(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (this.notRequiresAuthentication(httpServletRequest, securedRequestMatcher)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        securedFilterGateway.doFilterInternalSecured(httpServletRequest, httpServletResponse, filterChain, securedRequestMatcher, jwtSecurityExceptionHandling, jwtUserDetailsService);
    }

    private boolean notRequiresAuthentication(HttpServletRequest request, RequestMatcher requestMatcher) {
        if (!requestMatcher.matches(request)) {
            if (log.isTraceEnabled()) {
                log.trace("Did not match request to {} - {}", requestMatcher, request.getMethod());
            }
            return true;
        }
        return false;
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.validateFilterGateway = applicationContext.getBean(ValidateFilterGateway.class);
        this.loginFilterGateway = applicationContext.getBean(LoginFilterGateway.class);
        this.securedFilterGateway = applicationContext.getBean(SecuredFilterGateway.class);
        this.jwtSecurityExceptionHandling = applicationContext.getBean(JwtSecurityExceptionHandling.class);
        this.jwtUserDetailsService = applicationContext.getBean(JwtUserDetailsService.class);

        this.validateRequestMatcher = new AntPathRequestMatcher(jwtSecurityConfigurer.getJwtProperties().getPaths().getValidate().getPath());
        this.loginRequestMatcher = new AntPathRequestMatcher(jwtSecurityConfigurer.getJwtProperties().getPaths().getToken().getPath());
        this.securedRequestMatcher = new AntPathRequestMatcher(jwtSecurityConfigurer.getJwtProperties().getPaths().getSecured().getPath());
    }
}
