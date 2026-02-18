package com.staffrating.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    // Spring Boot auto-configuration handles DataSource and JPA setup.
    // No custom beans needed — this avoids conflicts with auto-config.
}