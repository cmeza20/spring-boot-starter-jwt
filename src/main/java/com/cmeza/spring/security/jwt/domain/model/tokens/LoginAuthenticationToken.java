package com.cmeza.spring.security.jwt.domain.model.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class LoginAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 7903875831574231360L;

    private final String username;
    private char[] password;

    public LoginAuthenticationToken(String username, char[] password) {
        this(username, password, Collections.emptyList());
    }

    public LoginAuthenticationToken(String username, String password) {
        this(username, password.toCharArray(), Collections.emptyList());
    }

    public LoginAuthenticationToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password.toCharArray(), authorities);
    }

    public LoginAuthenticationToken(String username, char[] password, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(false);
        this.username = username;
        this.password = password;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.password = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoginAuthenticationToken that = (LoginAuthenticationToken) o;
        return Objects.equals(username, that.username) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), username);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}
