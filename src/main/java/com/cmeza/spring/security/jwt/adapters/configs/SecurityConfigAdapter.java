package com.cmeza.spring.security.jwt.adapters.configs;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityExceptionHandling;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityFilterChain;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.configs.SecurityConfigGateway;
import com.cmeza.spring.security.jwt.domain.usecase.utils.enums.PathType;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Collections;
import java.util.Objects;

public class SecurityConfigAdapter extends InitializingComponent implements SecurityConfigGateway {

    private JwtSecurityFilterChain jwtSecurityFilterChain;
    private JwtSecurityExceptionHandling jwtSecurityExceptionHandling;

    @Override
    public SecurityFilterChain bindSecurity(HttpSecurity http, AbstractHttpConfigurer<?, HttpSecurity> httpConfigurer, JwtPathProperties pathProperties, PathType pathType) throws Exception {
        http.antMatcher(pathProperties.getPath()).apply(httpConfigurer);

        jwtSecurityFilterChain.globalConfigure(http, jwtSecurityExceptionHandling::entryPointHandler, jwtSecurityExceptionHandling::accessDeniedHandler);
        jwtSecurityFilterChain.configureFilter(http, pathType);

        String[] role = pathProperties.getRole();
        if (Objects.isNull(role) || role.length == 0) {
            return http.authorizeRequests().anyRequest().permitAll().and().build();
        }

        return http.authorizeRequests().anyRequest().access(hasAnyRole(pathProperties.getRolePrefix(), role)).and().build();
    }

    @Override
    public SimpleUrlHandlerMapping bindMapping(AbstractController abstractController, JwtPathProperties pathProperties) {
        return new SimpleUrlHandlerMapping(Collections.singletonMap(pathProperties.getPath(), abstractController));
    }

    @Override
    public String hasAnyRole(String rolePrefix, String... authorities) {
        if (Objects.isNull(rolePrefix)) rolePrefix = "";
        String anyAuthorities = StringUtils.arrayToDelimitedString(authorities, "','" + rolePrefix);
        return "hasAnyRole('" + rolePrefix + anyAuthorities + "')";
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtSecurityFilterChain = applicationContext.getBean(JwtSecurityFilterChain.class);
        this.jwtSecurityExceptionHandling = applicationContext.getBean(JwtSecurityExceptionHandling.class);
    }
}
