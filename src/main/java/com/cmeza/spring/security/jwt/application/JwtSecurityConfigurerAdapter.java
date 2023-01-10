package com.cmeza.spring.security.jwt.application;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.adapter.impl.JwtConfigurerBuilder;
import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.platform.impl.JwtAdvancedConfigurerImpl;
import com.cmeza.spring.security.jwt.application.properties.JwtProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class JwtSecurityConfigurerAdapter implements JwtSecurityConfigurer, InitializingBean, ApplicationContextAware {

    private JwtConfigurerBuilder jwtConfigurerBuilder;
    private JwtProperties jwtProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        JwtAdvancedConfigurerImpl jwtAdvancedConfigurer = new JwtAdvancedConfigurerImpl();
        this.advancedConfigure(jwtAdvancedConfigurer);
        jwtAdvancedConfigurer.build();

        this.jwtConfigurerBuilder = new JwtConfigurerBuilder(jwtAdvancedConfigurer);
        this.configure(this.jwtConfigurerBuilder);
        jwtConfigurerBuilder.build();
    }

    protected void configure(JwtConfigurer jwtConfigurer) {
    }

    protected void advancedConfigure(JwtAdvancedConfigurer jwtAdvancedConfigurer) {
    }

    @Override
    public JwtConfigurerBuilder getJwtAuthenticationManagerBuilder() {
        return jwtConfigurerBuilder;
    }

    @Override
    public JwtProperties getJwtProperties() {
        return jwtProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.jwtProperties = applicationContext.getBean(JwtProperties.class);
    }
}
