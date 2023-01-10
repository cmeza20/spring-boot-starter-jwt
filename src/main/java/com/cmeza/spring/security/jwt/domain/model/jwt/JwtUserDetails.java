package com.cmeza.spring.security.jwt.domain.model.jwt;

public class JwtUserDetails {
    private String username;
    private String token;

    public String getUsername() {
        return username;
    }

    public JwtUserDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getToken() {
        return token;
    }

    public JwtUserDetails setToken(String token) {
        this.token = token;
        return this;
    }
}
