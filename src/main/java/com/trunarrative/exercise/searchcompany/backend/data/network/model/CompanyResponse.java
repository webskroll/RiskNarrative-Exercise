package com.trunarrative.exercise.searchcompany.backend.data.network.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * Maps each fields from JSON to CompanyResponse
 */
@Data
@Getter
public class CompanyResponse {

    private int page_number;
    private String kind;
    private int total_results;
    private List<Company> items;

    @Getter
    public static class Company {

        private String company_status;
        private String address_snippet;
        private String date_of_creation;
        private Matches matches;
        private String description;
        private Links links;
        private String company_number;
        private String title;
        private String company_type;
        private Address address;
        private String kind;
    }

    @Getter
    public static class Matches {
        private List<Integer> title;
    }

    @Getter
    public static class Links {
        private String self;
    }
}
