package com.cmeza.spring.security.jwt.application.configurer.platform;

import com.cmeza.spring.security.jwt.domain.usecase.ports.output.configs.SecurityConfigGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.encoders.JwtTokenEncoder;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.SecuredFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.ValidateFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.LoginFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers.JwtHandlerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtEncodeManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtTokenManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers.JwtProviderGateway;
import org.springframework.boot.web.error.ErrorAttributeOptions;

import java.util.List;

public interface JwtAdvancedConfigurerDetails {
    List<ErrorAttributeOptions.Include> getIncludes();

    List<ErrorAttributeOptions.Include> getExcludes();

    List<JwtTokenEncoder> getEncoders();

    ValidateFilterGateway getValidateFilterGateway();


    LoginFilterGateway getLoginFilterGateway();

    SecuredFilterGateway getSecuredFilterGateway();


    SecurityConfigGateway getSecurityConfigGateway();


    JwtHandlerGateway getJwtHandlerGateway();


    JwtTokenManagerGateway getJwtTokenManagerGateway();


    JwtEncodeManagerGateway getJwtEncodeManagerGateway();


    <R, E> JwtProviderGateway<R, E> getJwtProviderGateway();
}
