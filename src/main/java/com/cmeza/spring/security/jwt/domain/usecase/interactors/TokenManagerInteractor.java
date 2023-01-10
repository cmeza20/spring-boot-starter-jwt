package com.cmeza.spring.security.jwt.domain.usecase.interactors;

import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtBuilderWrapper;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.managers.JwtTokenManagerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtTokenManagerGateway;
import io.jsonwebtoken.Claims;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class TokenManagerInteractor extends InitializingComponent implements JwtTokenManagerUseCase {

    private JwtTokenManagerGateway jwtTokenManagerGateway;

    @Override
    public User createUser(Authentication authentication, List<GrantedAuthority> authorities) {
        return jwtTokenManagerGateway.createUser(authentication, authorities);
    }

    @Override
    public JwtBuilderWrapper createJwtBuilder(String subject) {
        return jwtTokenManagerGateway.createJwtBuilder(subject);
    }

    @Override
    public Claims decodeJwt(String token) {
        return jwtTokenManagerGateway.decodeJwt(token);
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtTokenManagerGateway = applicationContext.getBean(JwtTokenManagerGateway.class);
    }
}
