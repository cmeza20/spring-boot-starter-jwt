package com.cmeza.spring.security.jwt.application.processors;

import com.cmeza.spring.security.jwt.application.configurer.adapter.JwtSecurityConfigurer;
import com.cmeza.spring.security.jwt.application.configurer.platform.JwtAdvancedConfigurerDetails;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class JwtDefaultErrorAttributes extends DefaultErrorAttributes implements ApplicationContextAware {

    private JwtAdvancedConfigurerDetails jwtAdvancedConfigurerDetails;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return super.getErrorAttributes(webRequest, options
                .including(jwtAdvancedConfigurerDetails.getIncludes().toArray(new ErrorAttributeOptions.Include[0]))
                .excluding(jwtAdvancedConfigurerDetails.getExcludes().toArray(new ErrorAttributeOptions.Include[0])));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.jwtAdvancedConfigurerDetails = applicationContext.getBean(JwtSecurityConfigurer.class).getJwtAuthenticationManagerBuilder().getJwtAdvancedConfigurerDetails();
    }
}
