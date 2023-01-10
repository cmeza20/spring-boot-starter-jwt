package com.cmeza.spring.security.jwt.application.properties;

import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConstructorBinding
public final class JwtPathProperties {

    /**
     * Endpoint path(String)
     */
    @NotNull
    private final String path;
    /**
     * Endpoint exposed(Boolean)
     */
    private final boolean exposed;
    /**
     * Endpoint role(String[] - Example: ROLE_USER, ROLE_ADMIN)
     */
    private final String[] role;
    /**
     * Endpoint prefix(String - Works only when "role" is defined)
     */
    private final String rolePrefix;

    public JwtPathProperties(String path, @DefaultValue("true") boolean exposed, String[] role, @DefaultValue(JwtConstants.ROLE) String rolePrefix) {
        this.path = cleanPath(path);
        this.exposed = exposed;
        this.role = role;
        this.rolePrefix = rolePrefix;
    }

    public String getPath() {
        return path;
    }

    public boolean isExposed() {
        return exposed;
    }

    public String[] getRole() {
        return role;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    private String cleanPath(String path) {
        String candidate = StringUtils.trimWhitespace(path);
        return StringUtils.hasText(candidate) && candidate.endsWith("/") ? candidate.substring(0, candidate.length() - 1) : candidate;
    }
}
