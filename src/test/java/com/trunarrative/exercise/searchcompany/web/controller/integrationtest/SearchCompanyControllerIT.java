package com.trunarrative.exercise.searchcompany.web.controller.integrationtest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import com.trunarrative.exercise.searchcompany.backend.service.RetrieveCompanyDetailsService;
import com.trunarrative.exercise.searchcompany.web.controller.SearchCompanyController;
import com.trunarrative.exercise.searchcompany.web.mapper.CompanyRequestMapper;
import com.trunarrative.exercise.searchcompany.web.mapper.CompanyResponseMapper;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyRequest;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchCompanyController.class)
@ExtendWith(SpringExtension.class)
public class SearchCompanyControllerIT {

    @Autowired private MockMvc mockMvc;
    @MockBean
    private RetrieveCompanyDetailsService retrieveCompanyDetailsService;
    @MockBean
    private CompanyRequestMapper companyRequestMapper;
    @MockBean
    private CompanyResponseMapper companyResponseMapper;

    @Autowired
    ObjectMapper objectMapper;

    private String requestJson(SearchCompanyRequest searchCompanyRequest) throws Exception{
        return objectMapper.writeValueAsString(searchCompanyRequest);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/company/get", "/company/get?onlyActive=false", "/company/get?onlyActive=true"})
    void shouldReturnOkWhenCorrectInputIsPassed(String url) throws Exception {
        var onlyActiveFlag = url.contains("onlyActive=true");
        var apiKey = "631378";
        var companyDTO =
                CompanyDTO.builder().apiKey(apiKey).onlyActive(onlyActiveFlag).companyName("BBC").companyNumber("211212").build();
        var companyDetailsDTO = mock(CompanyDetailsDTO.class);
        var response = SearchCompanyResponse.builder().build();

        when(companyRequestMapper.to(eq(onlyActiveFlag), eq(apiKey), any(SearchCompanyRequest.class))).thenReturn(
                companyDTO
        );
        when(retrieveCompanyDetailsService.getCompanyDetails(companyDTO)).thenReturn(companyDetailsDTO);
        when(companyResponseMapper.from(companyDetailsDTO)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", apiKey)
                .content(requestJson(new SearchCompanyRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}
