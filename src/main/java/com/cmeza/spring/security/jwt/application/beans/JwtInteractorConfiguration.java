package com.cmeza.spring.security.jwt.application.beans;

import com.cmeza.spring.security.jwt.domain.usecase.interactors.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public final class JwtInteractorConfiguration<R, E> {

    @Bean
    @ConditionalOnMissingBean
    public FilterInteractor<R, E> filterInteractor() {
        return new FilterInteractor<>();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityConfigInteractor securityConfigInteractor() {
        return new SecurityConfigInteractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public HandlerInteractor handlerInteractor() {
        return new HandlerInteractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenManagerInteractor tokenManagerInteractor() {
        return new TokenManagerInteractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public EncodeManagerInteractor encodeManagerInteractor() {
        return new EncodeManagerInteractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProviderInteractor<R, E> providerInteractor() {
        return new ProviderInteractor<>();
    }
}
