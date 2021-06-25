package com.unicorn.studio.dao;

import com.unicorn.studio.entity.StripePaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripePaymentIntentRepository extends JpaRepository<StripePaymentIntent,Long> {
}
