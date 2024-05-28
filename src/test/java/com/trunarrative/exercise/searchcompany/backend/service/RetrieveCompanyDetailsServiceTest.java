package com.trunarrative.exercise.searchcompany.backend.service;

import com.trunarrative.exercise.searchcompany.backend.data.NetworkCompanyService;
import com.trunarrative.exercise.searchcompany.backend.data.database.DBCompanyService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveCompanyDetailsServiceTest {

    @Mock private NetworkCompanyService networkCompanyService;
    @Mock private DBCompanyService dbCompanyService;

    @InjectMocks private RetrieveCompanyDetailsService retrieveCompanyDetailsService;

    @Test
    void shouldReturnResultFromDBWhenCompanyNumberIsProvided() {
        var companyNumber = "123";
        var companyDTO = CompanyDTO.builder().companyNumber(companyNumber).build();
        var expectedData = CompanyDetailsDTO.builder().build();
        when(dbCompanyService.get(companyNumber)).thenReturn(expectedData);

        assertThat(retrieveCompanyDetailsService.getCompanyDetails(companyDTO)).isEqualTo(expectedData);
        verifyNoInteractions(networkCompanyService);
    }

    @Test
    void shouldReturnResultFromNetworkServiceWhenCompanyNumberIsProvidedAndNoDataFoundInDB() {
        var companyNumber = "123";
        var companyDTO = CompanyDTO.builder().companyNumber(companyNumber).build();
        var expectedData = CompanyDetailsDTO.builder().build();
        when(dbCompanyService.get(companyNumber)).thenReturn(null);
        when(networkCompanyService.get(companyDTO)).thenReturn(expectedData);

        assertThat(retrieveCompanyDetailsService.getCompanyDetails(companyDTO)).isEqualTo(expectedData);
        verify(networkCompanyService).get(companyDTO);
        verify(dbCompanyService).save(expectedData);
    }

    @Test
    void shouldReturnResultFromNetworkServiceAndNoCallToDatabaseServiceWhenCompanyNumberIsNotProvided() {
        var companyNumber = "123";
        var companyDTO = CompanyDTO.builder().companyNumber(null).companyName(companyNumber).build();
        var expectedData = CompanyDetailsDTO.builder().build();
        when(networkCompanyService.get(companyDTO)).thenReturn(expectedData);

        assertThat(retrieveCompanyDetailsService.getCompanyDetails(companyDTO)).isEqualTo(expectedData);
        verify(dbCompanyService, times(0)).get(any());
        verify(dbCompanyService).save(expectedData);
    }

    @Test
    void shouldReturnActiveCompaniesWhenOnlyActiveFlagIsSetAndBothActiveAndInactiveCompaniesAreFound() {
        var companyNumber = "123";
        var activeCompany = CompanyDetailsDTO.CompanyDetails.builder().companyNumber("123").companyStatus("active").build();
        var inactiveCompany  = CompanyDetailsDTO.CompanyDetails.builder().companyNumber("456").companyStatus("not-active").build();
        var companyDTO = CompanyDTO.builder().onlyActive(true).companyName(companyNumber).build();
        var expectedData = CompanyDetailsDTO.builder()
                .items(List.of(
                        activeCompany,
                        inactiveCompany
                ))
                .build();
        when(networkCompanyService.get(companyDTO)).thenReturn(expectedData);

        var result = retrieveCompanyDetailsService.getCompanyDetails(companyDTO);
        assertThat(result.getItems()).containsExactly(activeCompany);
    }

    @Test
    void shouldReturnActiveOfficersWhenBothActiveAndInactiveOfficersAreFound() {
        var companyNumber = "123";
        var activeOfficers = OfficerDTO.builder().name("123").resignedOn(null).build();
        var inActiveOfficers = OfficerDTO.builder().name("345").resignedOn("23-12-2023").build();
        var activeCompany = CompanyDetailsDTO.CompanyDetails.builder().
                companyNumber("123").companyStatus("active").officers(
                        List.of(activeOfficers, inActiveOfficers)
                ).build();
        var companyDTO = CompanyDTO.builder().onlyActive(true).companyName(companyNumber).build();
        var expectedData = CompanyDetailsDTO.builder()
                .items(List.of(
                        activeCompany
                ))
                .build();
        when(networkCompanyService.get(companyDTO)).thenReturn(expectedData);

        var result = retrieveCompanyDetailsService.getCompanyDetails(companyDTO);
        assertThat(result.getItems().get(0).getOfficers()).containsExactly(activeOfficers);
    }
}