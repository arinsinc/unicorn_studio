package com.unicorn.studio.dao;

import com.unicorn.studio.entity.StripePaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripePaymentMethodRepository extends JpaRepository<StripePaymentMethod,Long> {
}
