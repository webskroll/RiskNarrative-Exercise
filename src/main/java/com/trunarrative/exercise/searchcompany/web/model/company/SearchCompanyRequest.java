package com.trunarrative.exercise.searchcompany.web.model.company;

import lombok.Getter;

/**
 * Holds fields for the request body
 */
@Getter
public class SearchCompanyRequest {

    private String companyName;
    private String companyNumber;

}
