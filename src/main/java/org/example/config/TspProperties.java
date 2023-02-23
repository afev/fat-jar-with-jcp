package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tsp")
@Data
public class TspProperties {
    private String signatureProvider;
    private String keyStoreProvider;
    private String keyStoreType;
    private String keyAlias;
    private String keyPassword;
}
