package com.unicorn.studio.projection;

import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.Industry;

import java.util.List;

public interface CompanyPortfolioProjection {
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
}
