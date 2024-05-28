package com.trunarrative.exercise.searchcompany.web.mapper;

import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyRequest;
import org.springframework.stereotype.Component;

/**
 * Maps request to CompanyDTO
 */
@Component
public class CompanyRequestMapper {

    public CompanyDTO to(
            final Boolean onlyActive,
            final String apiKey,
            final SearchCompanyRequest searchCompanyRequest) {
        return CompanyDTO.builder()
                .onlyActive(Boolean.TRUE.equals(onlyActive))
                .apiKey(apiKey)
                .companyNumber(searchCompanyRequest.getCompanyNumber())
                .companyName(searchCompanyRequest.getCompanyName()).build();
    }
}
