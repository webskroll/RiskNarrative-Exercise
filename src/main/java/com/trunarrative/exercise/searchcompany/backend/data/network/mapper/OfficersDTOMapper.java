package com.trunarrative.exercise.searchcompany.backend.data.network.mapper;

import com.trunarrative.exercise.searchcompany.backend.data.network.model.Address;
import com.trunarrative.exercise.searchcompany.backend.data.network.model.OfficerResponse;
import com.trunarrative.exercise.searchcompany.backend.model.AddressDTO;
import com.trunarrative.exercise.searchcompany.backend.model.OfficerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfficersDTOMapper {

    /**
     * This method maps OfficerDTO from OfficerResponse
     * @param officerResponse of type OfficerResponse
     * @return list of OfficerDTO
     */
    public List<OfficerDTO> from(final OfficerResponse officerResponse) {
        return officerResponse != null && officerResponse.getItems() != null
                ? officerResponse.getItems().stream()
                .map(officer ->
                        OfficerDTO.builder()
                                .name(officer.getName())
                                .officerRole(officer.getOfficer_role())
                                .appointedOn(officer.getAppointed_on())
                                .resignedOn(officer.getResigned_on())
                                .address(mapCompanyAddress(officer.getAddress())).build()
                ).collect(Collectors.toList()) : List.of();
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
