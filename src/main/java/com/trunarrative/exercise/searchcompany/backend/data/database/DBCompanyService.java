package com.trunarrative.exercise.searchcompany.backend.data.database;

import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;

/**
 * This service will connect to database perform operations
 */
public interface DBCompanyService {

    /**
     * Retrieve the company details based on company number
     * @param companyNumber company number
     * @return CompanyDetailsDTO
     */
    public CompanyDetailsDTO get(String companyNumber);

    /**
     * save company information in the company table
     * @param companyDetailsDTO of type companyDetailsDTO
     * @return returned the same object after getting saved.
     */
    public CompanyDetailsDTO save(final CompanyDetailsDTO companyDetailsDTO);

}
