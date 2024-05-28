package com.trunarrative.exercise.searchcompany.backend.data.network;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Initialise Web Client
 */
@Component
public class WebClientInitializer {

    public WebClient initialise(final String baseURL) {
        return WebClient.create(baseURL);
    }
}
