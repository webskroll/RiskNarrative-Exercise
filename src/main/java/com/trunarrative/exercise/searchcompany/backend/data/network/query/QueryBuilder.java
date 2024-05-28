package com.trunarrative.exercise.searchcompany.backend.data.network.query;

import com.trunarrative.exercise.searchcompany.backend.model.CompanyDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Central place for building query params for external API calls
 */
public class QueryBuilder {


    /**
     * This will build a query for external API
     * @param companyDTO of type CompanyDTO
     * @return of type MultiValueMap
     */
    public static MultiValueMap<String, String> searchCompanyQuery(final CompanyDTO companyDTO) {
        var map = new LinkedMultiValueMap<String, String>();
        map.put( "Query", List.of(query(companyDTO)));
        return map;
    }

    /**
     * This will build a query for external API
     * @param companyNumber of type String
     * @return of type MultiValueMap
     */
    public static MultiValueMap<String, String> searchOfficersQuery(final String companyNumber) {
        var map = new LinkedMultiValueMap<String, String>();
        map.put( "CompanyNumber", List.of(companyNumber));
        return map;
    }

    private static String query(final CompanyDTO companyDTO) {
        return companyDTO.getCompanyNumber() != null
                ? companyDTO.getCompanyNumber() : companyDTO.getCompanyName();
    }
}
