package com.cmeza.spring.security.jwt.application.configurer.platform.impl;

import com.cmeza.spring.security.jwt.adapters.configs.SecurityConfigAdapter;
import com.cmeza.spring.security.jwt.adapters.filters.SecuredFilterAdapter;
import com.cmeza.spring.security.jwt.adapters.filters.ValidateFilterAdapter;
import com.cmeza.spring.security.jwt.adapters.filters.LoginFilterAdapter;
import com.cmeza.spring.security.jwt.adapters.handlers.JwtHandlerAdapter;
import com.cmeza.spring.security.jwt.adapters.managers.JwtEncodeManagerAdapter;
import com.cmeza.spring.security.jwt.adapters.managers.JwtTokenManagerAdapter;
import com.cmeza.spring.security.jwt.adapters.providers.JwtProviderAdapter;
import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurerDetails;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JwtAdvancedConfigurerImpl implements JwtAdvancedConfigurer, JwtAdvancedConfigurerDetails {
    private final List<ErrorAttributeOptions.Include> includes = new ArrayList<>();
    private final List<ErrorAttributeOptions.Include> excludes = new ArrayList<>();
    private final List<JwtTokenEncoder> encoders = new ArrayList<>();

    private ValidateFilterGateway validateFilterGateway;
    private LoginFilterGateway loginFilterGateway;
    private SecuredFilterGateway securedFilterGateway;
    private SecurityConfigGateway securityConfigGateway;
    private JwtHandlerGateway jwtHandlerGateway;
    private JwtTokenManagerGateway jwtTokenManagerGateway;
    private JwtEncodeManagerGateway jwtEncodeManagerGateway;
    private JwtProviderGateway<?, ?> jwtProviderGateway;

    public JwtAdvancedConfigurerImpl() {
        includes.add(ErrorAttributeOptions.Include.MESSAGE);
    }

    @Override
    public JwtAdvancedConfigurer addErrorAttributeOptionsInclude(ErrorAttributeOptions.Include include) {
        this.includes.add(include);
        return this;
    }

    @Override
    public JwtAdvancedConfigurer addErrorAttributeOptionsExclude(ErrorAttributeOptions.Include exclude) {
        this.excludes.add(exclude);
        return this;
    }

    @Override
    public JwtAdvancedConfigurer addJwtEncoder(JwtTokenEncoder jwtTokenEncoder) {
        this.encoders.add(jwtTokenEncoder);
        return this;
    }

    @Override
    public List<ErrorAttributeOptions.Include> getIncludes() {
        return includes;
    }

    @Override
    public List<ErrorAttributeOptions.Include> getExcludes() {
        return excludes;
    }

    @Override
    public List<JwtTokenEncoder> getEncoders() {
        return encoders;
    }

    @Override
    public JwtAdvancedConfigurer setValidateFilterGateway(ValidateFilterGateway validateFilterGateway) {
        this.validateFilterGateway = validateFilterGateway;
        return this;
    }

    @Override
    public JwtAdvancedConfigurer setSecuredFilterGateway(SecuredFilterGateway securedFilterGateway) {
        this.securedFilterGateway = securedFilterGateway;
        return this;
    }

    @Override
    public JwtAdvancedConfigurer setLoginFilterGateway(LoginFilterGateway loginFilterGateway) {
        this.loginFilterGateway = loginFilterGateway;
        return this;
    }

    @Override
    public JwtAdvancedConfigurer setSecurityConfigGateway(SecurityConfigGateway securityConfigGateway) {
        this.securityConfigGateway = securityConfigGateway;
        return this;
    }

    @Override
    public JwtHandlerGateway getJwtHandlerGateway() {
        return jwtHandlerGateway;
    }

    @Override
    public JwtAdvancedConfigurer setJwtHandlerGateway(JwtHandlerGateway jwtHandlerGateway) {
        this.jwtHandlerGateway = jwtHandlerGateway;
        return this;
    }

    @Override
    public JwtTokenManagerGateway getJwtTokenManagerGateway() {
        return jwtTokenManagerGateway;
    }

    @Override
    public JwtAdvancedConfigurer setJwtTokenManagerGateway(JwtTokenManagerGateway jwtTokenManagerGateway) {
        this.jwtTokenManagerGateway = jwtTokenManagerGateway;
        return this;
    }

    @Override
    public JwtEncodeManagerGateway getJwtEncodeManagerGateway() {
        return jwtEncodeManagerGateway;
    }

    @Override
    public JwtAdvancedConfigurer setJwtEncodeManagerGateway(JwtEncodeManagerGateway jwtEncodeManagerGateway) {
        this.jwtEncodeManagerGateway = jwtEncodeManagerGateway;
        return this;
    }

    @Override
    public <R, E> JwtProviderGateway<R, E> getJwtProviderGateway() {
        return (JwtProviderGateway<R, E>) jwtProviderGateway;
    }

    @Override
    public <R, E> JwtAdvancedConfigurer setJwtProviderGateway(JwtProviderGateway<R, E> jwtProviderGateway) {
        this.jwtProviderGateway = jwtProviderGateway;
        return this;
    }

    @Override
    public ValidateFilterGateway getValidateFilterGateway() {
        return validateFilterGateway;
    }

    @Override
    public LoginFilterGateway getLoginFilterGateway() {
        return loginFilterGateway;
    }

    @Override
    public SecuredFilterGateway getSecuredFilterGateway() {
        return securedFilterGateway;
    }

    @Override
    public SecurityConfigGateway getSecurityConfigGateway() {
        return securityConfigGateway;
    }

    public void build() {
        if (Objects.isNull(validateFilterGateway)) {
            this.validateFilterGateway = new ValidateFilterAdapter();
        }
        if (Objects.isNull(loginFilterGateway)) {
            this.loginFilterGateway = new LoginFilterAdapter<>();
        }
        if (Objects.isNull(securedFilterGateway)) {
            this.securedFilterGateway = new SecuredFilterAdapter();
        }
        if (Objects.isNull(securityConfigGateway)) {
            this.securityConfigGateway = new SecurityConfigAdapter();
        }
        if (Objects.isNull(jwtHandlerGateway)) {
            this.jwtHandlerGateway = new JwtHandlerAdapter();
        }
        if (Objects.isNull(jwtTokenManagerGateway)) {
            this.jwtTokenManagerGateway = new JwtTokenManagerAdapter();
        }
        if (Objects.isNull(jwtEncodeManagerGateway)) {
            this.jwtEncodeManagerGateway = new JwtEncodeManagerAdapter();
        }
        if (Objects.isNull(jwtProviderGateway)) {
            this.jwtProviderGateway = new JwtProviderAdapter<>();
        }
    }

}
