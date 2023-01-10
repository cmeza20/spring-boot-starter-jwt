package com.cmeza.spring.security.jwt.application.security;

import com.cmeza.spring.security.jwt.application.configurer.adapter.impl.JwtLoggeable;
import com.cmeza.spring.security.jwt.application.properties.JwtPathProperties;
import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;

import java.util.Objects;

public abstract class AbstractSecurityConfig extends JwtLoggeable {

    @Override
    protected void log(Logger log, String spacer) {
        JwtPathProperties pathProperties = getPathProperties();
        log.debug("| Path: {} ", pathProperties.getPath());
        log.debug("| Role: {} ", Objects.isNull(pathProperties.getRole()) ? "Any role" : pathProperties.getRole());
        log.debug("| Role Prefix: {} ", pathProperties.getRolePrefix());
    }

    protected abstract JwtPathProperties getPathProperties();

    @Override
    protected LogLevel logLevel() {
        return LogLevel.DEBUG;
    }
}
