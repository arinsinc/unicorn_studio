package com.unicorn.studio.projection;

import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.Industry;

import java.util.List;

public interface CompanyProjection {
    String getUid();
    String getName();
    String getProfile();
    String getUrl();
    String getFoundedYear();
    String getOrgType();
    String getSize();
    Industry getIndustry();
    City getHeadquarter();
    List<MediaStorageProjection> getMedia();
    List<CompanyMetricsProjection> getCompanyMetrics();
    List<FundingProjection> getFunding();
    List<InvestorPortfolioProjection> getInvestors();
}
