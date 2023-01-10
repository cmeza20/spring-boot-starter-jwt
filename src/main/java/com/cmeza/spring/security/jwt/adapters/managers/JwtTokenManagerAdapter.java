package com.cmeza.spring.security.jwt.adapters.managers;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;
import com.cmeza.spring.security.jwt.domain.model.jwt.JwtBuilderWrapper;
import com.cmeza.spring.security.jwt.domain.usecase.initializing.InitializingComponent;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.managers.JwtTokenManagerGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.List;

public class JwtTokenManagerAdapter extends InitializingComponent implements JwtTokenManagerGateway {

    private JwtProperties jwtProperties;
    private Period period;

    @Override
    public User createUser(Authentication authentication, List<GrantedAuthority> authorities) {
        return new User((String) authentication.getPrincipal(), CharBuffer.wrap((char[]) authentication.getCredentials()).toString(), authorities);
    }

    @Override
    public JwtBuilderWrapper createJwtBuilder(String subject) {
        LocalDateTime time = LocalDateTime.now();

        Date createdAt = time.toDate();
        Date expiredAt = time.plus(period).toDate();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(Jwts.claims())
                .setIssuedAt(createdAt)
                .setExpiration(expiredAt)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getConfiguration().getSecretKey());

        return new JwtBuilderWrapper()
                .setCreatedAt(createdAt)
                .setExpiredAt(expiredAt)
                .setJwtBuilder(jwtBuilder);
    }

    @Override
    public Claims decodeJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getConfiguration().getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    protected void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer) {
        this.jwtProperties = jwtSecurityConfigurer.getJwtProperties();
        this.period = jwtProperties.getConfiguration().getTimeSuffix().getPeriodFormatter().parsePeriod(jwtProperties.getConfiguration().getTime());
    }
}
