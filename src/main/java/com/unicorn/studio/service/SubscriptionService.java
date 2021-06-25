package com.unicorn.studio.service;

import com.unicorn.studio.entity.Plan;
import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.projection.PlanProjection;
import com.unicorn.studio.projection.SubscriptionProjection;

import java.util.List;

public interface SubscriptionService {
    List<PlanProjection> getPlans();
    void savePlan(Plan plan);
    PlanProjection getPlan(String uid);
    void deletePlan(String uid);

    List<SubscriptionProjection> getSubscriptions();
    void saveSubscription(Subscription subscription);
    SubscriptionProjection getSubscription(String uid);
    void deleteSubscription(String uid);
}
