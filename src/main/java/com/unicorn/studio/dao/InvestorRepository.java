package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.projection.InvestorProjection;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, Long>, JpaSpecificationExecutor<Investor>, PagingAndSortingRepository<Investor, Long> {
    @Query("SELECT i.uid, i.fullName, i.email, i.title, i.profile, i.investorSince FROM Investor i inner join i.companies c WHERE c.id=:companyId")
    Optional<List<InvestorPortfolioProjection>> findByCompanies_Id(long companyId);

    @Query("SELECT i.uid, i.fullName, i.email, i.title, i.profile, i.investorSince FROM Investor i WHERE i.uid=:uid")
    Optional<InvestorProjection> findByUid(String uid);

    @Query("SELECT i.uid, i.fullName, i.email, i.title, i.profile, i.investorSince FROM Investor i WHERE i.fullName=:name")
    Optional<InvestorPortfolioProjection> findByFullName(String name);

    @Query("SELECT i.uid, i.fullName, i.email, i.title, i.profile, i.investorSince FROM Investor i")
    List<InvestorPortfolioProjection> findAllInvestors(Pageable pageable);

    void deleteByUid(String uid);
}
