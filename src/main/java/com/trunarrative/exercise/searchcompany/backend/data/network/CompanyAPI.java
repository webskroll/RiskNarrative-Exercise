package com.trunarrative.exercise.searchcompany.backend.data.network;

import com.trunarrative.exercise.searchcompany.backend.data.network.mapper.CompanyDetailsDTOMapper;
import com.trunarrative.exercise.searchcompany.backend.data.network.model.CompanyResponse;
import com.trunarrative.exercise.searchcompany.backend.data.network.query.QueryBuilder;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Company API to make calls to external company API
 */
@Component
@RequiredArgsConstructor
public final class CompanyAPI {
    private static final String PATH = "/Search";
    private final ExtendedWebClient extendedWebClient;
    private final CompanyDetailsDTOMapper companyDetailsDTOMapper;

    /**
     * Call ExtendedWebClient to make external Company API and maps the result to CompanyDetailsDTO
     * @param companyDTO of type CompanyDTO
     * @return CompanyDetailsDTO
     */
    public CompanyDetailsDTO call(final CompanyDTO companyDTO){
        Optional<CompanyResponse> companyResponseOptional = extendedWebClient.get(
                PATH,
                companyDTO.getApiKey(),
                QueryBuilder.searchCompanyQuery(companyDTO),
                CompanyResponse.class
                ).blockOptional();

        return companyResponseOptional.map(companyDetailsDTOMapper::from)
                .orElseThrow(() -> new RuntimeException("Payload is null"));
    }

}
