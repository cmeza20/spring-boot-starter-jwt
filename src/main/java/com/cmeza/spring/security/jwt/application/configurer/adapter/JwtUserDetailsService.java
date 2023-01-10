package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.domain.model.jwt.JwtDescriptor;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtBadCredentialsException;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtUserDetailsService<R, E> extends JwtUserDetailsServiceEvent {

    E authenticate(UserDetails userDetails, List<GrantedAuthority> authorities) throws JwtBadCredentialsException;

    LoginAuthenticationToken convert(R request) throws JwtAuthenticationException;

    void tokenCreated(E entity, JwtDescriptor jwtDescriptor);

    default Object tokenToObject(JwtDescriptor jwtDescriptor, List<GrantedAuthority> authorities) {
        return null;
    }

    default E tokenToEntity(JwtDescriptor jwtDescriptor, List<GrantedAuthority> authorities) {
        return null;
    }

    default void configureJwt(JwtBuilder jwtBuilder, E entity) {
    }
}
