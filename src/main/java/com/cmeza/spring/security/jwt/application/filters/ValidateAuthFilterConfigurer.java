package com.cmeza.spring.security.jwt.application.filters;

import com.cmeza.spring.security.jwt.domain.usecase.ports.input.filters.ValidateFilterUseCase;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateAuthFilterConfigurer extends AbstractHttpConfigurer<ValidateAuthFilterConfigurer, HttpSecurity> {
    private final ValidateFilterUseCase validateFilterUseCase;

    public ValidateAuthFilterConfigurer(ValidateFilterUseCase validateFilterUseCase) {
        this.validateFilterUseCase = validateFilterUseCase;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(new RequestFilter(), BasicAuthenticationFilter.class);
    }

    private final class RequestFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            validateFilterUseCase.doFilterInternalValidate(request, response, filterChain);
        }
    }


}
