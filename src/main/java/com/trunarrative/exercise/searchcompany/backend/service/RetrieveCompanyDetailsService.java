package com.trunarrative.exercise.searchcompany.backend.service;

import com.trunarrative.exercise.searchcompany.backend.data.NetworkCompanyService;
import com.trunarrative.exercise.searchcompany.backend.data.database.DBCompanyService;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RetrieveCompanyDetailsService {

    private final NetworkCompanyService networkCompanyService;
    private final DBCompanyService dbCompanyService;
    private final Predicate<String> IS_COMPANY_ACTIVE = "active"::equalsIgnoreCase;
    private final Predicate<String> IS_OFFICER_ACTIVE = Objects::isNull;

    /**
     * This method does the following
     * <ul>
     *     <li>Check company details in the database if consumer has passed the company number</li>
     *     <li>If not found in DB then get from external API</li>
     *     <li>Once result is found then save into DB</li>
     *     <li>Applying filtering i.e. show only active companies and officers</li>
     * </ul>
     * @param companyDTO of type CompanyDTO
     * @return of type CompanyDetailsDTO
     */
    public CompanyDetailsDTO getCompanyDetails
            (final CompanyDTO companyDTO) {
        if (companyDTO.isCompanyNumberSet()) {
            return Optional.ofNullable(dbCompanyService.get(companyDTO.getCompanyNumber()))
                    .orElseGet(() -> retrieveAndSave(companyDTO));

        } else {
           return retrieveAndSave(companyDTO);
        }
    }

    private CompanyDetailsDTO retrieveAndSave(final CompanyDTO companyDTO) {
        var companyDetailsDTO = networkCompanyService.get(companyDTO);

        dbCompanyService.save(companyDetailsDTO);

        if (companyDTO.isOnlyActive()) {
            return activeOfficersList(activeCompanyList(companyDetailsDTO));
        }
        return activeOfficersList(companyDetailsDTO);
    }

    // include only active companies
    private CompanyDetailsDTO activeCompanyList(final CompanyDetailsDTO companyDetailsDTO) {
        var filteredList = companyDetailsDTO.getItems().stream()
                .filter(item -> IS_COMPANY_ACTIVE.test(item.getCompanyStatus()))
                .collect(Collectors.toList());
        return CompanyDetailsDTO.builder().items(filteredList).totalResults(filteredList.size()).build();
    }

    private CompanyDetailsDTO activeOfficersList(final CompanyDetailsDTO companyDetailsDTO) {
        companyDetailsDTO.getItems().forEach(
                item ->  {
                   var activeOfficers = item.getOfficers().stream()
                            .filter(officer -> IS_OFFICER_ACTIVE.test(officer.getResignedOn())).collect(Collectors.toList());
                   item.setOfficers(activeOfficers);
                }
        );

        return companyDetailsDTO;
    }
}
