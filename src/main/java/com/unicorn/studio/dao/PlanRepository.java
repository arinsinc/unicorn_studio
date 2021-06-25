package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Plan;
import com.unicorn.studio.projection.PlanProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p.planName, p.planId, p.price FROM Plan p WHERE p.planId=:uid")
    Optional<PlanProjection> findByPlanId(String uid);

    void deleteByPlanId(String uid);

    @Query("SELECT p.planName, p.planId, p.price FROM Plan p")
    List<PlanProjection> findAllPlans();
}
