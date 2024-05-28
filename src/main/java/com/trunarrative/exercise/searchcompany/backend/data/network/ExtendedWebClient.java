package com.trunarrative.exercise.searchcompany.backend.data.network;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public final class ExtendedWebClient {

    private final WebClient webClient;

    public ExtendedWebClient(final URIConfig uriConfig,
                             final WebClientInitializer webClientInitializer) {
        webClient = webClientInitializer
                .initialise(uriConfig.getBaseURL() + uriConfig.getPath());

    }

    public <T> Mono<T> get(final String path,
                           final String apiKey,
                           final MultiValueMap<String, String> params,
                           Class<? extends T> clazzResponse
                           ) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .header("x-api-key", apiKey)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(clazzResponse));
    }

}
