package com.cmeza.spring.security.jwt.adapters.providers;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtUserDetailsService;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtBuilderWrapper;
import com.cmeza.spring.security.jwt.domain.model.tokens.JwtAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.model.tokens.LoginAuthenticationToken;
import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.managers.JwtEncodeManagerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.input.managers.JwtTokenManagerUseCase;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.providers.JwtProviderGateway;
import io.jsonwebtoken.Claims;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JwtProviderAdapter<R, E> extends InitializingComponent implements JwtProviderGateway<R, E> {

    private JwtEncodeManagerUseCase jwtEncodeManagerUseCase;
    private JwtTokenManagerUseCase jwtTokenManagerUseCase;

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtEncodeManagerUseCase = applicationContext.getBean(JwtEncodeManagerUseCase.class);
        this.jwtTokenManagerUseCase = applicationContext.getBean(JwtTokenManagerUseCase.class);
    }

    @Override
    public Authentication loginAuthenticate(LoginAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException {
        try {
            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

            User user = jwtTokenManagerUseCase.createUser(authentication, authorities);
            E entity = jwtUserDetailsService.authenticate(user, authorities);

            JwtBuilderWrapper jwtBuilderWrapper = jwtTokenManagerUseCase.createJwtBuilder(user.getUsername());

            jwtUserDetailsService.configureJwt(jwtBuilderWrapper.getJwtBuilder(), entity);

            String originalToken = jwtBuilderWrapper.getJwtBuilder().compact();
            String encodedtoken = jwtEncodeManagerUseCase.encode(originalToken);
            jwtBuilderWrapper.setOriginalToken(originalToken);
            jwtBuilderWrapper.setEncodedToken(encodedtoken);
            jwtBuilderWrapper.setEncoded(!originalToken.equals(encodedtoken));

            jwtUserDetailsService.tokenCreated(entity, jwtBuilderWrapper);

            if (Objects.isNull(entity)) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(entity, null, authorities);
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }

    @Override
    public Authentication jwtAuthenticate(JwtAuthenticationToken authentication, JwtUserDetailsService<R, E> jwtUserDetailsService) throws AuthenticationException {
        try {
            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

            String encodedToken = (String) authentication.getPrincipal();
            String originalToken = jwtEncodeManagerUseCase.decode(encodedToken);

            Claims claims = jwtTokenManagerUseCase.decodeJwt(originalToken);
            JwtBuilderWrapper jwtBuilderWrapper = this.makeJwtBuilderWrapper(originalToken, encodedToken, claims);

            Object principal = validateToken(jwtUserDetailsService, jwtBuilderWrapper, authorities, true);
            if (Objects.isNull(principal)) {
                principal = validateToken(jwtUserDetailsService, jwtBuilderWrapper, authorities, false);
                if (Objects.isNull(principal)) {
                    principal = jwtBuilderWrapper;
                }
            }
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage());
        }

    }

    private Object validateToken(JwtUserDetailsService<R, E> jwtUserDetailsService, JwtBuilderWrapper jwtBuilderWrapper, List<GrantedAuthority> authorities, boolean fromEntity) throws AuthenticationException {
        if (fromEntity) {
            return jwtUserDetailsService.tokenToEntity(jwtBuilderWrapper, authorities);
        } else {
            return jwtUserDetailsService.tokenToObject(jwtBuilderWrapper, authorities);
        }
    }

    private JwtBuilderWrapper makeJwtBuilderWrapper(String originalToken, String encodedToken, Claims claims) {
        return new JwtBuilderWrapper()
                .setEncodedToken(encodedToken)
                .setOriginalToken(originalToken)
                .setEncoded(!encodedToken.equals(originalToken))
                .setCreatedAt(claims.getIssuedAt())
                .setExpiredAt(claims.getExpiration())
                .setClaims(claims);
    }
}
