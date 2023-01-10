package com.cmeza.spring.security.jwt.application.configurer.adapter.impl;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtDescriptor;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtUser;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtUserDetails;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtBadCredentialsException;
import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class JwtUserDetailsServiceDefault extends JwtLoggeable implements JwtUserDetailsService<JwtUser, JwtUserDetails> {

    private static final String USERNAME = "user";
    private final String password;

    public JwtUserDetailsServiceDefault() {
        password = UUID.randomUUID().toString();
        this.printLog();
    }

    @Override
    public JwtUserDetails authenticate(UserDetails userDetails, List<GrantedAuthority> authorities) throws JwtBadCredentialsException {
        if (!userDetails.getUsername().equals(USERNAME) || !userDetails.getPassword().equals(password)) {
            throw new JwtBadCredentialsException("Bad credentials!");
        }
        return new JwtUserDetails().setUsername(userDetails.getUsername());
    }

    @Override
    public LoginAuthenticationToken convert(JwtUser request) throws JwtAuthenticationException {
        return new LoginAuthenticationToken(request.getUsername(), request.getPassword());
    }

    @Override
    public void tokenCreated(JwtUserDetails entity, JwtDescriptor jwtDescriptor) {
        entity.setToken(jwtDescriptor.getEncodedToken());
    }

    @Override
    protected void log(Logger log, String spacer) {
        log.info("| Jwt request: {}", JwtUser.info());
        log.info("| Jwt User: {}", USERNAME);
        log.info("| Jwt Password: {}", password);
    }

    @Override
    protected String logTitle() {
        return "Default Jwt credentials";
    }

    @Override
    protected LogLevel logLevel() {
        return LogLevel.INFO;
    }
}
