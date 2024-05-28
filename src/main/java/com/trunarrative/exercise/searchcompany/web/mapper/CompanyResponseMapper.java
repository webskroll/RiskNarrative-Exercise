package com.trunarrative.exercise.searchcompany.web.mapper;

import com.trunarrative.exercise.searchcompany.backend.model.AddressDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import com.trunarrative.exercise.searchcompany.backend.model.OfficerDTO;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map from CompanyDetailsDTO to SearchCompanyResponse
 */
@Component
public class CompanyResponseMapper {

    public SearchCompanyResponse from(final CompanyDetailsDTO companyDetailsDTO) {
        return SearchCompanyResponse.builder()
                .totalResults(companyDetailsDTO.getTotalResults())
                .items(mapCompanyDetails(companyDetailsDTO)).build();
    }


    private List<SearchCompanyResponse.CompanyDetails> mapCompanyDetails(
            final CompanyDetailsDTO companyDetailsDTO) {
        return companyDetailsDTO.getItems().stream()
                .map(this::mapEachCompanyDetails).collect(Collectors.toList());
    }

    private SearchCompanyResponse.CompanyDetails mapEachCompanyDetails(
            final CompanyDetailsDTO.CompanyDetails companyDetails
    ) {
        return SearchCompanyResponse.CompanyDetails
                .builder()
                .companyNumber(companyDetails.getCompanyNumber())
                .companyType(companyDetails.getCompanyType())
                .title(companyDetails.getTitle())
                .companyStatus(companyDetails.getCompanyStatus())
                .dateOfCreation(companyDetails.getDateOfCreation())
                .address(mapCompanyAddress(companyDetails.getAddress()))
                .officers(mapOfficers(companyDetails.getOfficers()))
                .build();
    }

    private SearchCompanyResponse.Address mapCompanyAddress(
            final AddressDTO addressDTO
    ) {
        return SearchCompanyResponse.Address
                .builder()
                .locality(addressDTO.getLocality())
                .postalCode(addressDTO.getPostalCode())
                .premises(addressDTO.getPremises())
                .addressLine1(addressDTO.getAddressLine1())
                .country(addressDTO.getCountry())
                .build();
    }


    private List<SearchCompanyResponse.Officers> mapOfficers(
            final List<OfficerDTO> officersDTO
    ) {
        return officersDTO.stream().map(
                this::mapOfficer
        ).collect(Collectors.toList());
    }

    private SearchCompanyResponse.Officers mapOfficer(
            final OfficerDTO officerDTO
    ) {
       return SearchCompanyResponse.Officers.builder()
               .name(officerDTO.getName())
               .officerRole(officerDTO.getOfficerRole())
               .appointedOn(officerDTO.getAppointedOn())
               .address(mapCompanyAddress(officerDTO.getAddress()))
               .build();
    }
}
