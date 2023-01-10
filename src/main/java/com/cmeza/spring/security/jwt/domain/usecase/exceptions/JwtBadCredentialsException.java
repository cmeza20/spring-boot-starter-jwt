package com.cmeza.spring.security.jwt.domain.usecase.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtBadCredentialsException extends AuthenticationException {

    private static final long serialVersionUID = -8993631051673312011L;

    public JwtBadCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtBadCredentialsException(String msg) {
        super(msg);
    }
}
