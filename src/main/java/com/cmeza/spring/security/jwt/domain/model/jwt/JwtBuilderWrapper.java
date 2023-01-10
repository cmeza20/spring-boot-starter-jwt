package com.cmeza.spring.security.jwt.domain.model.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;

import java.util.Date;

public class JwtBuilderWrapper implements JwtDescriptor {

    private Date createdAt;
    private Date expiredAt;
    @JsonIgnore
    private JwtBuilder jwtBuilder;
    private String originalToken;
    private String encodedToken;
    private boolean encoded;
    private Claims claims;

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public Date getExpiredAt() {
        return expiredAt;
    }

    @Override
    public boolean isEncoded() {
        return encoded;
    }

    @Override
    public String getOriginalToken() {
        return originalToken;
    }

    @Override
    public String getEncodedToken() {
        return encodedToken;
    }

    @Override
    public Claims getClaims() {
        return claims;
    }

    public JwtBuilder getJwtBuilder() {
        return jwtBuilder;
    }

    public JwtBuilderWrapper setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JwtBuilderWrapper setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public JwtBuilderWrapper setJwtBuilder(JwtBuilder jwtBuilder) {
        this.jwtBuilder = jwtBuilder;
        return this;
    }

    public JwtBuilderWrapper setOriginalToken(String originalToken) {
        this.originalToken = originalToken;
        return this;
    }

    public JwtBuilderWrapper setEncodedToken(String encodedToken) {
        this.encodedToken = encodedToken;
        return this;
    }

    public JwtBuilderWrapper setEncoded(boolean encoded) {
        this.encoded = encoded;
        return this;
    }

    public JwtBuilderWrapper setClaims(Claims claims) {
        this.claims = claims;
        return this;
    }
}
