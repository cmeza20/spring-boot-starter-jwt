package com.cmeza.spring.security.jwt.adapters.encoders;

import com.cmeza.spring.security.jwt.domain.usecase.ports.output.encoders.JwtTokenEncoder;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

public class Base64JwtTokenEncoder implements JwtTokenEncoder {

    @Override
    public String encode(String token) {
        return Base64Utils.encodeToString(token.getBytes());
    }

    @Override
    public String decode(String token) {
        return new String(Base64Utils.decodeFromString(token), StandardCharsets.UTF_8);
    }

    @Override
    public int order() {
        return -1;
    }
}
