package com.unicorn.studio.dao;

import com.unicorn.studio.entity.StripeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripeCustomerRepository extends JpaRepository<StripeCustomer,Long> {
    StripeCustomer findByEmail(String email);
}
