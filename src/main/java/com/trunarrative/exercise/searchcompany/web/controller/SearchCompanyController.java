package com.trunarrative.exercise.searchcompany.web.controller;

import com.trunarrative.exercise.searchcompany.backend.service.RetrieveCompanyDetailsService;
import com.trunarrative.exercise.searchcompany.web.mapper.CompanyRequestMapper;
import com.trunarrative.exercise.searchcompany.web.mapper.CompanyResponseMapper;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyRequest;
import com.trunarrative.exercise.searchcompany.web.model.company.SearchCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Company Controller
 */
@RestController
@RequestMapping("/company")
public class SearchCompanyController {

    @Autowired
    private RetrieveCompanyDetailsService retrieveCompanyDetailsService;
    @Autowired
    private CompanyRequestMapper companyRequestMapper;
    @Autowired
    private CompanyResponseMapper companyResponseMapper;

    /**
     *
     * @param onlyActive optional request parameter
     * @param apiKey mandatory heaader field
     * @param searchCompanyRequest request body
     * @return of type ResponseEntity returns json
     */
    @PostMapping("/get")
    public ResponseEntity<SearchCompanyResponse> company(
            @RequestParam(required = false) Boolean onlyActive,
            @RequestHeader(name = "x-api-key" ) String apiKey,
            @RequestBody SearchCompanyRequest searchCompanyRequest){
        var companyDetailsDTO = retrieveCompanyDetailsService
                .getCompanyDetails(companyRequestMapper.to(true, apiKey, searchCompanyRequest));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(companyResponseMapper.from(companyDetailsDTO));
    }
}
