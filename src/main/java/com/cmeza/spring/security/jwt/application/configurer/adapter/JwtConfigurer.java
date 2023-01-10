package com.cmeza.spring.security.jwt.application.configurer.adapter;

public interface JwtConfigurer {

    <R, E> JwtConfigurer setJwtUserDetailsService(JwtUserDetailsService<R, E> jwtUserDetailsService);

    JwtConfigurer setJwtSecurityExceptionHandling(JwtSecurityExceptionHandling jwtSecurityExceptionHandling);

    JwtConfigurer setJwtSecurityFilterChain(JwtSecurityFilterChain jwtSecurityFilterChain);

}
