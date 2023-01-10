package com.cmeza.spring.security.jwt.domain.usecase.ports.output.encoders;

public interface JwtTokenEncoder {
    String encode(String token);

    String decode(String token);

    default int order() {
        return 0;
    }
}
