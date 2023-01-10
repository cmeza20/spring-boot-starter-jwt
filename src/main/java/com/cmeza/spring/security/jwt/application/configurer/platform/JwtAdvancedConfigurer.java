package com.cmeza.spring.security.jwt.application.configurer.platform;

import com.cmeza.spring.security.jwt.domain.usecase.ports.output.configs.SecurityConfigGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.encoders.JwtTokenEncoder;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.LoginFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.SecuredFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.filters.ValidateFilterGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers.JwtHandlerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtEncodeManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtTokenManagerGateway;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers.JwtProviderGateway;
import org.springframework.boot.web.error.ErrorAttributeOptions;

public interface JwtAdvancedConfigurer {

    JwtAdvancedConfigurer addErrorAttributeOptionsInclude(ErrorAttributeOptions.Include include);

    JwtAdvancedConfigurer addErrorAttributeOptionsExclude(ErrorAttributeOptions.Include exclude);

    JwtAdvancedConfigurer addJwtEncoder(JwtTokenEncoder jwtTokenEncoder);

    JwtAdvancedConfigurer setValidateFilterGateway(ValidateFilterGateway validateFilterGateway);

    JwtAdvancedConfigurer setSecuredFilterGateway(SecuredFilterGateway securedFilterGateway);

    JwtAdvancedConfigurer setLoginFilterGateway(LoginFilterGateway loginFilterGateway);

    JwtAdvancedConfigurer setSecurityConfigGateway(SecurityConfigGateway securityConfigGateway);

    JwtAdvancedConfigurer setJwtHandlerGateway(JwtHandlerGateway jwtHandlerGateway);

    JwtAdvancedConfigurer setJwtTokenManagerGateway(JwtTokenManagerGateway jwtTokenManagerGateway);

    JwtAdvancedConfigurer setJwtEncodeManagerGateway(JwtEncodeManagerGateway jwtEncodeManagerGateway);

    <R, E> JwtAdvancedConfigurer setJwtProviderGateway(JwtProviderGateway<R, E> jwtProviderGateway);
}
