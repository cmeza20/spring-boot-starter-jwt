package com.cmeza.spring.security.jwt.application.conditionals;

import com.cmeza.spring.security.jwt.application.JwtSecurityConfigurerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnBean(HttpSecurity.class)
@ConditionalOnSingleCandidate(JwtSecurityConfigurerAdapter.class)
public @interface ConditionalOnEnableWebSecurity {
}
