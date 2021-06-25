package com.unicorn.studio.service;

import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.specification.CompanySpecification;
import com.unicorn.studio.specification.InvestorSpecification;
import com.unicorn.studio.specification.StartupProgramSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface SearchService {
    Page<Company> getCompanySearch(Specification spec, Pageable pageable);
    Page<Investor> getInvestorSearch(Specification spec, Pageable pageable);
    Page<StartupProgram> getStartupProgramSearch(Specification spec, Pageable pageable);
    CompanySpecification extractCompanyFilterValues(String keyword, Map<String, String> map);
    InvestorSpecification extractInvestorFilterValues(String keyword, Map<String,String> map);
    StartupProgramSpecification extractStartupProgramFilterValues(String keyword, Map<String, String> map);
}
