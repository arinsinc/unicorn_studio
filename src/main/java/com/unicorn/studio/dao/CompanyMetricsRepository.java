package com.unicorn.studio.dao;

import com.unicorn.studio.entity.CompanyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMetricsRepository extends JpaRepository<CompanyMetrics, Long> {
}
