package com.cmeza.spring.security.jwt.application.configurer.adapter;

import com.cmeza.spring.security.jwt.application.configurer.adapter.impl.JwtConfigurerBuilder;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;

public interface JwtSecurityConfigurer {

    JwtConfigurerBuilder getJwtAuthenticationManagerBuilder();

    JwtProperties getJwtProperties();

}
