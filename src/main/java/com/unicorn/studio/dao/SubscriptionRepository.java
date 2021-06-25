package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.projection.SubscriptionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s.uid, s.startDate, s.endDate FROM Subscription s WHERE s.uid=:uid")
    Optional<SubscriptionProjection> findByUid(String uid);

    void deleteByUid(String uid);

    @Query("SELECT s.uid, s.startDate, s.endDate FROM Subscription s")
    List<SubscriptionProjection> findAllSubscriptions();
}
