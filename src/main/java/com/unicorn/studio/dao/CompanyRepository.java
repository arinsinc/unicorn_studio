package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Company;
import com.unicorn.studio.projection.CompanyProjection;
import com.unicorn.studio.projection.CompanyPortfolioProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company>, PagingAndSortingRepository<Company, Long> {
    @Query("SELECT c.uid, c.name, c.profile, c.url, c.foundedYear, c.orgType, c.size FROM Company c WHERE c.name=:name")
    List<CompanyPortfolioProjection> findCompanyByName(String name);

    @Query("SELECT c.uid, c.name, c.profile, c.url, c.foundedYear, c.orgType, c.size FROM Company c WHERE c.uid=:uid")
    Optional<CompanyProjection> findByUid(String uid);

    void deleteByUid(String uid);

    @Query("SELECT c.uid, c.name, c.profile, c.url, c.foundedYear, c.orgType, c.size FROM Company c")
    List<CompanyPortfolioProjection> findAllCompanies(Pageable pageable);

    List<Company> findByName(String name);
}
