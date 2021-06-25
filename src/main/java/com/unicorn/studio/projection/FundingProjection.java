package com.unicorn.studio.projection;

import java.time.LocalDateTime;

public interface FundingProjection {
    String getUid();
    long getAmount();
    char getCurrency();
    String getFundingType();
    LocalDateTime getFundingDate();
}
