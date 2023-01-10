package com.cmeza.spring.security.jwt.adapters.managers;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.encoders.JwtTokenEncoder;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtEncodeManagerGateway;
import org.springframework.context.ApplicationContext;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtEncodeManagerAdapter extends InitializingComponent implements JwtEncodeManagerGateway {

    private List<JwtTokenEncoder> jwtTokenEncoders;

    @Override
    public String encode(String token) {
        return this.jwtTokenEncoderProcess(token, true);
    }

    @Override
    public String decode(String token) {
        return this.jwtTokenEncoderProcess(token, false);
    }

    private String jwtTokenEncoderProcess(String token, boolean encode) {
        if (Objects.isNull(jwtTokenEncoders)) {
            return token;
        }

        Comparator<JwtTokenEncoder> comparing = Comparator.comparing(JwtTokenEncoder::order);
        if (!encode) {
            comparing = comparing.reversed();
        }

        jwtTokenEncoders = jwtTokenEncoders.stream()
                .sorted(comparing)
                .collect(Collectors.toList());

        for (JwtTokenEncoder jwtTokenEncoder : jwtTokenEncoders) {
            if (encode) {
                token = jwtTokenEncoder.encode(token);
            } else {
                token = jwtTokenEncoder.decode(token);
            }
        }
        return token;
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtTokenEncoders = jwtSecurityConfigurer.getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails().getEncoders();
    }
}
