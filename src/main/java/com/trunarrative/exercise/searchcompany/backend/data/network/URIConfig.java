package com.trunarrative.exercise.searchcompany.backend.data.network;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * POJO to holds external properties to build the external client URL
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.company-search.remote-client")
@EnableConfigurationProperties
public class URIConfig {
    private String baseURL;
    private String path;

}
