package com.trunarrative.exercise.searchcompany.backend.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Holds officer details
 */
@Builder
@Getter
public class OfficerDTO {
    private String name;
    private String officerRole;
    private String appointedOn;
    private AddressDTO address;
    private String resignedOn;
}
