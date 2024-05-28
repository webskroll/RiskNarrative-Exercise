package com.trunarrative.exercise.searchcompany.backend.model;


import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds Company and officers Details
 */
@Builder
@Data
public class CompanyDetailsDTO {

    int totalResults;
    @Builder.Default List<CompanyDetails> items =  new ArrayList<>();

    @Builder
    @Data
    public static class CompanyDetails {
         String companyNumber;
         String companyType;
         String title;
         String companyStatus;
         String dateOfCreation;
         AddressDTO address;
         @Setter @Builder.Default List<OfficerDTO> officers =  new ArrayList<>();
    }
}
