package com.bis.presentation.controller;

import com.bis.business.service.CsvImportService;
import com.bis.business.service.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring configuration class.
 * Defines beans that are shared across all layers.
 */
@Configuration
public class AppConfig {

    /**
     * BCryptPasswordEncoder is a Spring Security utility bean.
     * Injected into UserService to hash and verify passwords.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
