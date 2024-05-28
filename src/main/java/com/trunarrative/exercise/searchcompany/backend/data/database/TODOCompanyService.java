package com.trunarrative.exercise.searchcompany.backend.data.database;

import com.trunarrative.exercise.searchcompany.backend.model.CompanyDetailsDTO;
import org.springframework.stereotype.Component;

/**
 * This is not implemented yet
 */
@Component
public class TODOCompanyService implements DBCompanyService{
    @Override
    public CompanyDetailsDTO get(String companyNumber) {
        return null;
    }

    @Override
    public CompanyDetailsDTO save(CompanyDetailsDTO companyDetailsDTO) {
        return companyDetailsDTO;
    }
}
