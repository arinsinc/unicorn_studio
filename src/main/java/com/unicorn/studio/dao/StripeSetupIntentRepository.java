package com.unicorn.studio.dao;

import com.unicorn.studio.entity.StripeSetupIntent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripeSetupIntentRepository extends JpaRepository<StripeSetupIntent,Long> {
}
