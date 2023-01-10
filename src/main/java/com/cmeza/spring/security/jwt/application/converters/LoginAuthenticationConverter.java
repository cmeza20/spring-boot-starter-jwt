package com.cmeza.spring.security.jwt.application.converters;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.utils.GenericUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

public class LoginAuthenticationConverter<R> implements AuthenticationConverter {

    private static final ObjectMapper MAPPER;
    private final JwtSecurityConfigurer jwtSecurityConfigurer;
    private final JwtUserDetailsService<R, ?> jwtUserDetailsService;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public LoginAuthenticationConverter(JwtSecurityConfigurer jwtSecurityConfigurer, JwtUserDetailsService<R, ?> jwtUserDetailsService) {
        this.jwtSecurityConfigurer = jwtSecurityConfigurer;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        R resource = prepareToTransform(request);
        if (Objects.isNull(resource)) {
            throw new JwtAuthenticationException("Unresolved resource");
        }

        Set<ConstraintViolation<R>> constraintViolations = this.constraintValidation(resource);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        LoginAuthenticationToken loginAuthenticationToken = jwtUserDetailsService.convert(resource);
        if (!jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().isConfigured()) {
            JwtPathProperties pathProperties = jwtSecurityConfigurer.getJwtProperties().getPaths().getToken();
            if (Objects.nonNull(pathProperties.getRole()) && pathProperties.getRole().length > 0) {
                List<GrantedAuthority> authorities = Arrays.stream(pathProperties.getRole())
                        .map(r -> pathProperties.getRolePrefix() + r)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                return new LoginAuthenticationToken((String)loginAuthenticationToken.getPrincipal(), (char[])loginAuthenticationToken.getCredentials(), authorities);
            }
        }
        return loginAuthenticationToken;
    }

    private R prepareToTransform(HttpServletRequest httpServletRequest) {
        try {
            Class<R> requestClass = GenericUtils.getResponseClass(jwtUserDetailsService);
            return MAPPER.readValue(httpServletRequest.getInputStream(), requestClass);
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage(), e);
        }
    }

    private Set<ConstraintViolation<R>> constraintValidation(R resource) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator().validate(resource);
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }
}
