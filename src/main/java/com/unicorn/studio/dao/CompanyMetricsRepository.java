package com.unicorn.studio.dao;



import com.unicorn.studio.entity.CompanyMetrics;
import com.unicorn.studio.projection.CompanyMetricsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyMetricsRepository extends JpaRepository<CompanyMetrics, Long> {
    @Query("SELECT m.uid, m.revenue, m.unit, m.durationMonth FROM CompanyMetrics m WHERE m.company.id=:companyId")
    List<CompanyMetricsProjection> findByCompanyId(long companyId);

    @Query("SELECT m.uid, m.revenue, m.unit, m.durationMonth FROM CompanyMetrics m WHERE m.uid=:uid")
    Optional<CompanyMetricsProjection> findByUid(String uid);

    void deleteByUid(String uid);
}
