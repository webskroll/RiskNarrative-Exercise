package com.trunarrative.exercise.searchcompany.backend.data.network.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * Maps each fields from JSON to Officer Response
 */
@Data
@Getter
public class OfficerResponse {
    private String etag;
    private Links links;
    private String kind;
    private int items_per_page;
    private List<Officer> items;

    @Getter
    public static class Officer {
        private Address address;
        private String name;
        private String appointed_on;
        private String resigned_on;
        private String officer_role;
        private DOB date_of_birth;
        private String occupation;
        private String country_of_residence;
        private String nationality;
    }

    @Getter
    public static class DOB {
        private int month;
        private int year;
    }
}
