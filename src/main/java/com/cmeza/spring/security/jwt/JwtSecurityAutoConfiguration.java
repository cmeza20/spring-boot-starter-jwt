package com.cmeza.spring.security.jwt;

import com.cmeza.spring.security.jwt.application.beans.JwtGatewayConfiguration;
import com.cmeza.spring.security.jwt.application.beans.JwtInteractorConfiguration;
import com.cmeza.spring.security.jwt.application.conditionals.ConditionalOnEnableWebSecurity;
import com.cmeza.spring.security.jwt.application.conditionals.OverrideBeanCondition;
import com.cmeza.spring.security.jwt.application.processors.JwtDefaultErrorAttributes;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;
import com.cmeza.spring.security.jwt.application.security.SecuredSecurityConfig;
import com.cmeza.spring.security.jwt.application.security.TokenSecurityConfig;
import com.cmeza.spring.security.jwt.application.security.ValidateSecurityConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

@ConditionalOnEnableWebSecurity
@Import({
        JwtGatewayConfiguration.class,
        JwtInteractorConfiguration.class,
        TokenSecurityConfig.class,
        ValidateSecurityConfig.class,
        SecuredSecurityConfig.class
})
@EnableConfigurationProperties(JwtProperties.class)
public final class JwtSecurityAutoConfiguration {

    @Bean
    @Conditional(OverrideBeanCondition.class)
    public ErrorAttributes errorAttributes() {
        return new JwtDefaultErrorAttributes();
    }

}
