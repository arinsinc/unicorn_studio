package com.unicorn.studio.projection;

import com.unicorn.studio.entity.Industry;

import java.time.LocalDateTime;
import java.util.List;

public interface InvestorPortfolioProjection {
    String getUid();
    String getFullName();
    String getEmail();
    String getTitle();
    String getProfile();
    LocalDateTime getInvestorSince();
    List<MediaStorageProjection> getMedia();
    List<Industry> getIndustries();
}
