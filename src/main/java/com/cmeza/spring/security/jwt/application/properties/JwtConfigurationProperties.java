package com.cmeza.spring.security.jwt.application.properties;

import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@ConstructorBinding
public final class JwtConfigurationProperties {

    /**
    * Security secret key(String)
    */
    @NotBlank
    private final String secretKey;

    /**
     * Security token prefix(String)
     */
    @NotNull
    private final String tokenPrefix;

    /**
     * Security token lifetime(DateTime example: 1d 5h 30seg)
     */
    @NotBlank
    private final String time;

    @Valid
    @NestedConfigurationProperty
    private final JwtPeriodSuffixProperties timeSuffix;

    public JwtConfigurationProperties(String secretKey, @DefaultValue(JwtConstants.TOKEN_PREFIX) String tokenPrefix, @DefaultValue(JwtConstants.TIME) String time, @DefaultValue JwtPeriodSuffixProperties timeSuffix) {
        this.secretKey = secretKey;
        this.tokenPrefix = tokenPrefix;
        this.time = time;
        this.timeSuffix = timeSuffix;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTime() {
        return time;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public JwtPeriodSuffixProperties getTimeSuffix() {
        return timeSuffix;
    }
}
