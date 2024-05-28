package com.trunarrative.exercise.searchcompany.testconfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.trunarrative.exercise.searchcompany.backend.data.network.ExtendedWebClient;
import com.trunarrative.exercise.searchcompany.backend.data.network.URIConfig;
import com.trunarrative.exercise.searchcompany.backend.data.network.WebClientInitializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
public class MockWebClientConfig {

    @Bean
    public WireMockServer webServer() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        return wireMockServer;
    }

    @Bean
    public URIConfig uriConfig(final WireMockServer mockServer) {
        var uriConfig = new URIConfig();
        uriConfig.setBaseURL(mockServer.baseUrl());
        uriConfig.setPath("/TruProxyAPI/rest/Companies/v1");
        return uriConfig;
    }

    @Bean
    public WebClientInitializer webClientInitializer() {
        return new WebClientInitializer();
    }

    @Bean
    public ExtendedWebClient extendedWebClient(final URIConfig uriConfig,
                                               final WebClientInitializer webClientInitializer) {
        return new ExtendedWebClient(uriConfig, webClientInitializer);
    }

}
