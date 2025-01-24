package com.deskly.desklylocation.shared.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT")
                .allowedHeaders("Content-Type", "X-Amz-Date", "Authorization", "X-Api-Key", "X-Amz-Security-Token")
                .exposedHeaders("Content-Type", "X-Amz-Date", "Authorization", "X-Api-Key", "X-Amz-Security-Token")
                .allowCredentials(false);
    }
}
