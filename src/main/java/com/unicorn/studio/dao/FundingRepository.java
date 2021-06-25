package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.projection.FundingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    @Query("SELECT f.uid, f.amount, f.currency, f.fundingType, f.fundingDate FROM Funding f WHERE f.company.id=:companyId")
    List<FundingProjection> findByCompanyId(long companyId);

    @Query("SELECT f.uid, f.amount, f.currency, f.fundingType, f.fundingDate FROM Funding f WHERE f.uid=:uid")
    Optional<FundingProjection> findByUid(String uid);

    void deleteByUid(String uid);
}
