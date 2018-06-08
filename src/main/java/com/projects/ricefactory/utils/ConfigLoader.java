package com.projects.ricefactory.utils;

import com.projects.ricefactory.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigLoader {

    @Autowired
    Environment env;

    @Bean
    public TokenService tokenService() {
        return new TokenService(env);
    }

}
