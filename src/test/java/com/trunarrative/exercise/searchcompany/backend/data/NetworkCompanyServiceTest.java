package com.trunarrative.exercise.searchcompany.backend.data;

import com.trunarrative.exercise.searchcompany.backend.data.network.CompanyAPI;
import com.trunarrative.exercise.searchcompany.backend.data.network.OfficersAPI;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import com.trunarrative.exercise.searchcompany.backend.model.OfficerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class NetworkCompanyServiceTest {

    @Mock private  CompanyAPI companyAPI;
    @Mock private  OfficersAPI officersAPI;

    @InjectMocks private NetworkCompanyService networkCompanyService;

    @Test
    void shouldReturnCompanyDetailsFromBothCompanyAndOfficerAPIs(){
        var apiKey = "122";
        var companyNumber = "122333";
       var companyDTO = CompanyDTO.builder().apiKey(apiKey).companyNumber(companyNumber).build();
       var companyDetails = CompanyDetailsDTO.builder()
               .items(List.of(CompanyDetailsDTO.CompanyDetails.builder().companyNumber(companyNumber).build()))
               .build();
       var officerDTO = List.of(
               OfficerDTO.builder().build()
       );
        when(companyAPI.call(companyDTO)).thenReturn(
                companyDetails
        );
        when(officersAPI.call(apiKey, companyNumber)).thenReturn(
                officerDTO
        );


       var result = networkCompanyService.get(companyDTO);

        // expoected result
        companyDetails.getItems().get(0).setOfficers(officerDTO);
        assertThat(result).isEqualTo(companyDetails);
    }

    @Test
    void shouldReturnNullWhenNoCompanyFound(){
        var apiKey = "122";
        var companyNumber = "122333";
        var companyDTO = CompanyDTO.builder().apiKey(apiKey).companyNumber(companyNumber).build();
        var companyDetails = CompanyDetailsDTO.builder()
                .items(List.of(CompanyDetailsDTO.CompanyDetails.builder().companyNumber(companyNumber).build()))
                .build();
        var officerDTO = List.of(
                OfficerDTO.builder().build()
        );
        when(companyAPI.call(companyDTO)).thenReturn(
                null
        );


        assertThat( networkCompanyService.get(companyDTO)).isNull();
        verifyNoInteractions(officersAPI);
    }
}