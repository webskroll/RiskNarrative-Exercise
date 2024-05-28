package com.trunarrative.exercise.searchcompany.backend.data.remoteclient.integrationtest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.trunarrative.exercise.searchcompany.backend.data.network.ExtendedWebClient;
import com.trunarrative.exercise.searchcompany.backend.data.network.OfficersAPI;
import com.trunarrative.exercise.searchcompany.backend.data.network.URIConfig;
import com.trunarrative.exercise.searchcompany.backend.data.network.mapper.OfficersDTOMapper;
import com.trunarrative.exercise.searchcompany.testconfig.MockWebClientConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.trunarrative.exercise.searchcompany.TestUtil.loadJson;
import static org.assertj.core.api.Assertions.assertThat;

@Import({MockWebClientConfig.class, OfficerAPIIT.OfficersAPIConfiguration.class,
        JacksonAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
public class OfficerAPIIT {

    @TestConfiguration
    static class OfficersAPIConfiguration {

        @Bean
        public OfficersDTOMapper officersDTOMapper() {
            return new OfficersDTOMapper();
        }
        @Bean
        public OfficersAPI officersAPI(final ExtendedWebClient extendedWebClient,
                                     final OfficersDTOMapper officersDTOMapper) {
            return new OfficersAPI(extendedWebClient, officersDTOMapper);
        }
    }

    @Autowired
    private WireMockServer server;

    @Autowired
    private OfficersAPI officersAPI;

    @Autowired
    private URIConfig uriConfig;

    @Test
    void shouldMapJSONResultToModel() {
        var jsonString = loadJson("/sample/successful_officers_response.json");
        server.stubFor(
                WireMock.get(WireMock
                                .urlPathMatching( uriConfig.getPath() + "/Officers"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(jsonString)));

        var result = officersAPI.call("apikey", "companyName");
        assertThat(result).isNotNull();
    }
}
