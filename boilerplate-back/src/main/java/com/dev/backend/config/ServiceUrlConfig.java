package com.dev.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.services")
public record ServiceUrlConfig(
        String product, String referentiel) {
}
