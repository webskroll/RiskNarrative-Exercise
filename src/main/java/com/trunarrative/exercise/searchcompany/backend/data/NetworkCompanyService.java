package com.trunarrative.exercise.searchcompany.backend.data;

import com.trunarrative.exercise.searchcompany.backend.data.network.CompanyAPI;
import com.trunarrative.exercise.searchcompany.backend.data.network.OfficersAPI;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import com.trunarrative.exercise.searchcompany.backend.model.OfficerDTO;
import com.trunarrative.exercise.searchcompany.backend.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NetworkCompanyService {

    private final CompanyAPI companyAPI;
    private final OfficersAPI officersAPI;

    /**
     * This method does the following
     * <ul>
     *     <li>Get External call to get company list</li>
     *     <li>For each company, Get External call to get officers list</li>
     *     <li> and Builds into CompanyDetailsDTO</li>
     * </ul>
     * @param companyDTO of type CompanyDTO
     * @return company and officers list CompanyDetailsDTO
     */
    public CompanyDetailsDTO get(final CompanyDTO companyDTO) {
        var companyDetailsDTO = companyAPI.call(companyDTO);

        if (companyDetailsDTO != null ) {
            companyDetailsDTO.getItems().stream()
                    .map(company -> Pair.of(company, officersAPI.call(companyDTO.getApiKey(),
                            company.getCompanyNumber())))
                    .forEach(companyOfficersPair -> updateCompanyDTO(
                            companyOfficersPair.getFirst(),
                            companyOfficersPair.getSecond()));

        }
        return companyDetailsDTO;
    }

    private void updateCompanyDTO(final CompanyDetailsDTO.CompanyDetails companyDetails,
                                    List<OfficerDTO> officerDTOList) {
         companyDetails.setOfficers(officerDTOList);
    }
}
