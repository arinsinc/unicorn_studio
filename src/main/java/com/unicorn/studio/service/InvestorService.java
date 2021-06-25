package com.unicorn.studio.service;

import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.projection.EquityProjection;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import com.unicorn.studio.projection.InvestorProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvestorService {
    List<InvestorPortfolioProjection> getInvestors(Pageable pageable);
    void saveInvestor(Investor investor);
    InvestorProjection getInvestor(String uid);
    InvestorPortfolioProjection getInvestorByName(String name);
    void deleteInvestor(String uid);

    List<EquityProjection> getEquities(String investorId);
    void saveEquity(Equity equity);
    EquityProjection getEquity(String uid);
    void deleteEquity(String uid);
}
