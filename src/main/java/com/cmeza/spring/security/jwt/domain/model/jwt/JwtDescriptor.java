package com.cmeza.spring.security.jwt.domain.model.jwt;

import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtDescriptor {
    Date getCreatedAt();

    Date getExpiredAt();

    boolean isEncoded();

    String getOriginalToken();

    String getEncodedToken();

    Claims getClaims();
}
