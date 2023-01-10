package com.cmeza.spring.security.jwt.domain.model.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotBlank;

public class JwtUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public static JwtUser info() {
        return new JwtUser().setUsername("[YOUR USERNAME]").setPassword("[YOUR PASSWORD]");
    }

    public String getUsername() {
        return username;
    }

    public JwtUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JwtUser setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "JwtUser{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
