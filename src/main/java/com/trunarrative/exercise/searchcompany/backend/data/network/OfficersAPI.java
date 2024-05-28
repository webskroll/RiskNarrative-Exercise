package com.trunarrative.exercise.searchcompany.backend.data.network;

import com.trunarrative.exercise.searchcompany.backend.data.network.mapper.OfficersDTOMapper;
import com.trunarrative.exercise.searchcompany.backend.data.network.model.OfficerResponse;
import com.trunarrative.exercise.searchcompany.backend.model.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.trunarrative.exercise.searchcompany.backend.data.network.query.QueryBuilder.searchOfficersQuery;


@Component
@RequiredArgsConstructor
public class OfficersAPI {

    private static final String PATH = "/Officers";
    private final ExtendedWebClient extendedWebClient;
    private final OfficersDTOMapper officersDTOMapper;

    /**
     * Call ExtendedWebClient to make external Officer API and maps the result to CompanyDetailsDTO
     * @param apiKey API key
     * @param companyNumber  of type String
     * @return list of OfficerDTO
     */
    public List<OfficerDTO> call(final String apiKey,
                                 final String companyNumber){
        Optional<OfficerResponse> officersResponseOptional = extendedWebClient.get(
                PATH,
                apiKey,
                searchOfficersQuery(companyNumber),
                OfficerResponse.class
        ).blockOptional();

        return officersResponseOptional.map(officersDTOMapper::from)
                .orElseThrow(() -> new RuntimeException("Officers Result is null"));
    }
}
