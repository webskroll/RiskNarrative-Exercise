package com.trunarrative.exercise.searchcompany.backend.data.remoteclient.integrationtest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.trunarrative.exercise.searchcompany.backend.data.network.CompanyAPI;
import com.trunarrative.exercise.searchcompany.backend.data.network.ExtendedWebClient;
import com.trunarrative.exercise.searchcompany.backend.data.network.URIConfig;
import com.trunarrative.exercise.searchcompany.backend.data.network.mapper.CompanyDetailsDTOMapper;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
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

@Import({MockWebClientConfig.class, CompanyAPIIT.CompanyAPIConfiguration.class,
        JacksonAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
public class CompanyAPIIT {

    @TestConfiguration
    static class CompanyAPIConfiguration {

        @Bean
        public CompanyDetailsDTOMapper companyDetailsDTOMapper() {
            return new CompanyDetailsDTOMapper();
        }
        @Bean
        public CompanyAPI companyAPI(final ExtendedWebClient extendedWebClient,
                                     final CompanyDetailsDTOMapper companyDetailsDTOMapper) {
            return new CompanyAPI(extendedWebClient, companyDetailsDTOMapper);
        }
    }

    @Autowired
    private WireMockServer server;

    @Autowired
    private CompanyAPI companyAPI;

    @Autowired
    private URIConfig uriConfig;

    @Test
    void shouldMapJSONResultToModel() {
        var jsonString = loadJson("/sample/successful_company_response.json");
        server.stubFor(
                WireMock.get(WireMock
                                .urlPathMatching( uriConfig.getPath() + "/Search"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(jsonString)));

        var result = companyAPI.call(CompanyDTO.builder()
                        .companyName("BBC")
                        .companyNumber("12345")
                .build());
        assertThat(result).isNotNull();
    }
}
