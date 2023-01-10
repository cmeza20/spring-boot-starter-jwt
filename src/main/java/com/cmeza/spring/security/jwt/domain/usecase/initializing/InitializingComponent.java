package com.cmeza.spring.security.jwt.domain.usecase.initializing;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class InitializingComponent implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private JwtSecurityConfigurer jwtSecurityConfigurer;

    protected abstract void afterPropertiesSetComponent(ApplicationContext applicationContext, JwtSecurityConfigurer jwtSecurityConfigurer);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.jwtSecurityConfigurer = applicationContext.getBean(JwtSecurityConfigurer.class);
        afterPropertiesSetComponent(applicationContext, jwtSecurityConfigurer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public JwtSecurityConfigurer getJwtSecurityConfigurer() {
        return jwtSecurityConfigurer;
    }
}
