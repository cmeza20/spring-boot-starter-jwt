package com.cmeza.spring.security.jwt.application.processors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class JwtEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        application.setAllowBeanDefinitionOverriding(true);
        System.setProperty("logging.level.org.springframework.security.web", "warn");
        System.setProperty("spring.autoconfigure.exclude", "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration");
    }

}
