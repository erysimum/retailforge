package com.amitshahi.retailforge.catalog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(int pageSize) {
    public ApplicationProperties {
        if (pageSize <= 0) {
            pageSize = 10;
        }
    }
}
