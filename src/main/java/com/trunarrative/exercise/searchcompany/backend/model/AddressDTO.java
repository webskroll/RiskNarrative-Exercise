package com.trunarrative.exercise.searchcompany.backend.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Stores address information
 */
@Builder
@Getter
public class AddressDTO {

    private String locality;
    private String postalCode;
    private String premises;
    private String addressLine1;
    private String country;
}
