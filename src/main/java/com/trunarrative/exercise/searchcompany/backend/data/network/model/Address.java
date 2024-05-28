package com.trunarrative.exercise.searchcompany.backend.data.network.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * Stores address information maps to JSON received from external API.
 */
@Getter
@Builder
@Value
public class Address {
    String premises;
    String postal_code;
    String country;
    String locality;
    String address_line_1;

}
