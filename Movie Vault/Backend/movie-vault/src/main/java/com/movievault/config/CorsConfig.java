package com.movievault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${spring.web.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
    
        if ("*".equals(allowedOrigins)) {
            config.addAllowedOrigin("*");
        } else {
            String[] origins = allowedOrigins.split(",");
            Arrays.stream(origins).forEach(config::addAllowedOrigin);
        }
        
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(!"*".equals(allowedOrigins));
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}