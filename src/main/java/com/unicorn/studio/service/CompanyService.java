package com.unicorn.studio.service;

import com.unicorn.studio.entity.*;
import com.unicorn.studio.projection.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    List<CompanyPortfolioProjection> getCompanies(Pageable pageable);
    void saveCompany(Company company);
    CompanyProjection getCompany(String uid);
    CompanyPortfolioProjection getCompanyByName(String name);
    Company formatCompany(Object obj);
    void deleteCompany(String uid);

    List<CompanyMetricsProjection> getAllCompanyMetrics(String companyUId);
    void saveCompanyMetrics(CompanyMetrics companyMetrics);
    CompanyMetricsProjection getCompanyMetrics(String uid);
    void deleteCompanyMetrics(String uid);

    List<FundingProjection> getFundingList(String companyId);
    void saveFunding(Funding funding);
    FundingProjection getFunding(String uid);
    void deleteFunding(String uid);
}
