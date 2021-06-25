package com.unicorn.studio.projection;

import java.time.LocalDate;

public interface SubscriptionProjection {
    String getUid();
    LocalDate getStartDate();
    LocalDate getEndDate();
    PlanProjection getPlan();
}
