package com.cmeza.spring.security.jwt.adapters.handlers;

import com.cmeza.spring.security.jwt.domain.usecase.exceptions.JwtAuthenticationException;
import com.cmeza.spring.security.jwt.domain.usecase.ports.output.handlers.JwtHandlerGateway;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class JwtHandlerAdapter implements JwtHandlerGateway {

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            throw new JwtAuthenticationException("Not authenticated");
        } else {
            MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
            mappingJackson2JsonView.setPrefixJson(false);
            mappingJackson2JsonView.setExtractValueFromSingleKeyModel(true);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(mappingJackson2JsonView);
            modelAndView.addObject(authentication.getPrincipal());
            return modelAndView;
        }

    }
}
