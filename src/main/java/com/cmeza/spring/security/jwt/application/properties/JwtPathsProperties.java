package com.cmeza.spring.security.jwt.application.properties;

import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public final class JwtPathsProperties {

    @Valid
    @NestedConfigurationProperty
    private JwtPathProperties token = new JwtPathProperties(JwtConstants.TOKEN_PATH, Boolean.TRUE, null, JwtConstants.ROLE);

    @Valid
    @NestedConfigurationProperty
    private JwtPathProperties validate = new JwtPathProperties(JwtConstants.VALIDATE_PATH, Boolean.TRUE, null, JwtConstants.ROLE);

    @Valid
    @NestedConfigurationProperty
    private JwtPathProperties secured = new JwtPathProperties(JwtConstants.SECURED_PATH, Boolean.TRUE, null, JwtConstants.ROLE);

    public JwtPathProperties getToken() {
        return token;
    }

    public JwtPathsProperties setToken(JwtPathProperties token) {
        this.token = token;
        return this;
    }

    public JwtPathProperties getValidate() {
        return validate;
    }

    public JwtPathsProperties setValidate(JwtPathProperties validate) {
        this.validate = validate;
        return this;
    }

    public JwtPathProperties getSecured() {
        return secured;
    }

    public JwtPathsProperties setSecured(JwtPathProperties secured) {
        this.secured = secured;
        return this;
    }
}
