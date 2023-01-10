package com.cmeza.spring.security.jwt.application.beans;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityFilterChain;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.configs.SecurityConfigGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.SecuredFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.ValidateFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.LoginFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers.JwtHandlerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtEncodeManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtTokenManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers.JwtProviderGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public final class JwtGatewayConfiguration {

    private final JwtSecurityConfigurer jwtSecurityConfigurer;

    public JwtGatewayConfiguration(JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtSecurityConfigurer = jwtSecurityConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean
    public <R, E> JwtUserDetailsService<R, E> jwtUserDetailsService() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtSecurityExceptionHandling jwtSecurityExceptionHandling() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtSecurityExceptionHandling();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtSecurityFilterChain jwtSecurityFilterChain() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtSecurityFilterChain();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidateFilterGateway validateFilterGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getValidateFilterGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginFilterGateway loginFilterGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getLoginFilterGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecuredFilterGateway securedFilterGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getSecuredFilterGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityConfigGateway securityConfigGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getSecurityConfigGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtHandlerGateway jwtHandlerGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getJwtHandlerGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenManagerGateway jwtTokenManagerGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getJwtTokenManagerGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtEncodeManagerGateway jwtEncodeManagerGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getJwtEncodeManagerGateway();
    }

    @Bean
    @ConditionalOnMissingBean
    public <R, E> JwtProviderGateway<R, E> jwtProviderGateway() {
        return jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getJwtProviderGateway();
    }
}
