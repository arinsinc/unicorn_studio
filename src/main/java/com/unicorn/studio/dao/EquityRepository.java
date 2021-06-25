package com.unicorn.studio.dao;



import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.projection.EquityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EquityRepository extends JpaRepository<Equity, Long> {
    @Query("SELECT e.uid, e.share, e.allottedIn FROM Equity e WHERE e.investor.id=:investorId")
    List<EquityProjection> findByInvestorId(long investorId);

    @Query("SELECT e.uid, e.share, e.allottedIn FROM Equity e WHERE e.uid=:uid")
    Optional<EquityProjection> findByUid(String uid);

    void deleteByUid(String uid);
}
