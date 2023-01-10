package com.cmeza.spring.security.jwt.application.properties;

import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@ConstructorBinding
@ConfigurationProperties(JwtConstants.PROPERTY_PREFIX)
public class JwtProperties {

    @Valid
    @NestedConfigurationProperty
    private final JwtConfigurationProperties configuration;

    @Valid
    @NestedConfigurationProperty
    private final JwtPathsProperties paths;

    public JwtProperties(@DefaultValue JwtConfigurationProperties configuration, @DefaultValue JwtPathsProperties paths) {
        this.configuration = configuration;
        this.paths = paths;
    }

    public JwtConfigurationProperties getConfiguration() {
        return configuration;
    }

    public JwtPathsProperties getPaths() {
        return paths;
    }
}
