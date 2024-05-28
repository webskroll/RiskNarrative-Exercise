package com.trunarrative.exercise.searchcompany.backend.data.network.mapper;

import com.trunarrative.exercise.searchcompany.backend.data.network.model.Address;
import com.trunarrative.exercise.searchcompany.backend.data.network.model.CompanyResponse;
import com.trunarrative.exercise.searchcompany.backend.model.AddressDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper to build CompanyDetailsDTO
 */
@Component
public class CompanyDetailsDTOMapper {

    /**
     * This method maps CompanyDetailsDTO from CompanyResponse
     * @param companyResponse of tyep CompanyResponse
     * @return CompanyDetailsDTO
     */
    public CompanyDetailsDTO from(final CompanyResponse companyResponse) {
        return CompanyDetailsDTO.builder().
                totalResults(companyResponse.getTotal_results())
                .items(mapCompanyDetails(companyResponse)).build();
    }

    private List<CompanyDetailsDTO.CompanyDetails> mapCompanyDetails(
            final CompanyResponse companyResponse) {
        return companyResponse.getItems().stream()
                .map(this::mapEachCompanyDetails).collect(Collectors.toList());
    }

    private CompanyDetailsDTO.CompanyDetails mapEachCompanyDetails(
            final CompanyResponse.Company company
    ) {
        return CompanyDetailsDTO.CompanyDetails
                .builder()
                .companyNumber(company.getCompany_number())
                .companyType(company.getCompany_type())
                .title(company.getTitle())
                .companyStatus(company.getCompany_status())
                .dateOfCreation(company.getDate_of_creation())
                .address(mapCompanyAddress(company.getAddress())).build();
    }

    private AddressDTO mapCompanyAddress(
            final Address address
    ) {
        return AddressDTO
                .builder()
                .locality(address.getLocality())
                .postalCode(address.getPostal_code())
                .premises(address.getPremises())
                .addressLine1(address.getAddress_line_1())
                .country(address.getCountry()).build();
    }
}
