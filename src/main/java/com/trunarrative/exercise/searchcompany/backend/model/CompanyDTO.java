package com.trunarrative.exercise.searchcompany.backend.model;


import lombok.Builder;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

/**
 * Holds Company information
 */
@Builder
@Value
public class CompanyDTO {

    boolean onlyActive;
    String apiKey;
    String companyName;
    String companyNumber;

    /**
     * true of set otherwise false
     * @return true or false
     */
    public boolean isCompanyNumberSet() {
        return !Strings.isEmpty(this.companyNumber);
    }

}
