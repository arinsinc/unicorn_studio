package com.unicorn.studio.service;

import com.unicorn.studio.dao.PlanRepository;
import com.unicorn.studio.dao.SubscriptionRepository;
import com.unicorn.studio.entity.Plan;
import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.PlanProjection;
import com.unicorn.studio.projection.SubscriptionProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImp implements SubscriptionService{
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImp(PlanRepository planRepository, SubscriptionRepository subscriptionRepository) {
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Get all plans
     * @return List<Plan>
     */
    @Override
    @Transactional
    public List<PlanProjection> getPlans() {
        return planRepository.findAllPlans();
    }

    /**
     * Save plan
     * @param plan
     */
    @Override
    @Transactional
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    /**
     * Get plan by uid
     * @param uid
     * @return plan
     */
    @Override
    @Transactional
    public PlanProjection getPlan(String uid) {
        Optional<PlanProjection> result = planRepository.findByPlanId(uid);
        PlanProjection plan = null;
        if (result.isPresent()) {
            plan = result.get();
        }
        else {
            throw new NotFoundException("Did not find plan id: " + uid);
        }
        return plan;
    }

    /**
     * Delete plan by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deletePlan(String uid) {
        planRepository.deleteByPlanId(uid);
    }

    /**
     * Get all subscriptions
     * @return List<Subscription>
     */
    @Override
    @Transactional
    public List<SubscriptionProjection> getSubscriptions() {
        return subscriptionRepository.findAllSubscriptions();
    }

    /**
     * Save Subscription
     * @param subscription
     */
    @Override
    @Transactional
    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    /**
     * Get subscription by uid
     * @param uid
     * @return subscription
     */
    @Override
    @Transactional
    public SubscriptionProjection getSubscription(String uid) {
        Optional<SubscriptionProjection> result = subscriptionRepository.findByUid(uid);
        SubscriptionProjection subscription = null;
        if (result.isPresent()) {
            subscription = result.get();
        }
        else {
            throw new NotFoundException("Did not find subscription id: " + uid);
        }
        return subscription;
    }

    /**
     * Delete subscription by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteSubscription(String uid) {
        subscriptionRepository.deleteByUid(uid);
    }
}
