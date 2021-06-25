package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.service.SearchService;
import com.unicorn.studio.specification.CompanySpecification;
import com.unicorn.studio.specification.InvestorSpecification;
import com.unicorn.studio.specification.StartupProgramSpecification;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search/company")
    public ResponseEntity<ResponseSerializer> searchCompanies(
            @RequestParam("keyword") String keyword,
            @RequestParam(required = false) Map<String, String> filters,
            Pageable pageable
    ) {
        int pageNo = 0;
        if (filters.containsKey("pageNo")) {
            pageNo = Integer.parseInt(filters.get("pageNo"));
            filters.remove("pageNo");
        }
        CompanySpecification spec = searchService.extractCompanyFilterValues(keyword, filters);
        pageable = PageRequest.of(pageNo,20);
        Page<Company> companyPage = searchService.getCompanySearch(spec, pageable);
        ResponseSerializer response = new ResponseSerializer(true, "success","Companies search result", companyPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/investor")
    public ResponseEntity<ResponseSerializer> searchInvestors(
            @RequestParam("keyword") String keyword,
            @RequestParam(required = false) Map<String, String> filters,
            Pageable pageable
    ) {
        int pageNo = 0;
        if (filters.containsKey("pageNo")) {
            pageNo = Integer.parseInt(filters.get("pageNo"));
            filters.remove("pageNo");
        }
        InvestorSpecification spec = searchService.extractInvestorFilterValues(keyword,filters);
        pageable = PageRequest.of(pageNo,20);
        Page<Investor> investorPage = searchService.getInvestorSearch(spec, pageable);
        ResponseSerializer response = new ResponseSerializer(true, "success","Investors search result", investorPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/startup-program")
    public ResponseEntity<ResponseSerializer> searchStartupPrograms(
            @RequestParam("keyword") String keyword,
            @RequestParam(required = false) Map<String, String> filters,
            Pageable pageable
    ) {
        int pageNo = 0;
        if (filters.containsKey("pageNo")) {
            pageNo = Integer.parseInt(filters.get("pageNo"));
            filters.remove("pageNo");
        }
        StartupProgramSpecification spec = searchService.extractStartupProgramFilterValues(keyword,filters);
        pageable = PageRequest.of(pageNo,25);
        Page<StartupProgram> startupProgramPage = searchService.getStartupProgramSearch(spec, pageable);
        ResponseSerializer response = new ResponseSerializer(true, "success","Startup programs search result", startupProgramPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
