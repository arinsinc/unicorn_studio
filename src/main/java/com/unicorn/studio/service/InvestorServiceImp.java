package com.unicorn.studio.service;

import com.unicorn.studio.dao.EquityRepository;
import com.unicorn.studio.dao.InvestorRepository;
import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.EquityProjection;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import com.unicorn.studio.projection.InvestorProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InvestorServiceImp implements InvestorService{
    @Autowired
    private EquityRepository equityRepository;

    @Autowired
    private InvestorRepository investorRepository;

    public InvestorServiceImp(EquityRepository equityRepository, InvestorRepository investorRepository) {
        this.equityRepository = equityRepository;
        this.investorRepository = investorRepository;
    }

    /**
     * Get all investors
     * @return List<Investor>
     */
    @Override
    @Transactional
    public List<InvestorPortfolioProjection> getInvestors(Pageable pageable) {
        return investorRepository.findAllInvestors(pageable);
    }

    /**
     * Save investor
     * @param investor
     */
    @Override
    @Transactional
    public void saveInvestor(Investor investor) {
        Optional<Investor> userInvestor = investorRepository.findById(investor.getId());
        if (userInvestor == null) {
            investor.setId(0L);
        }
        investorRepository.save(investor);
    }

    /**
     * Get investor by uid
     * @param uid
     * @return investor
     */
    @Override
    @Transactional
    public InvestorProjection getInvestor(String uid) {
        Optional<InvestorProjection> result = investorRepository.findByUid(uid);
        InvestorProjection investor = null;
        if (result.isPresent()) {
            investor = result.get();
        }
        else {
            throw new NotFoundException("Did not find investor id: " + uid);
        }
        return investor;
    }

    /**
     * Get investor by name
     * @param name
     * @return investor
     */
    @Override
    @Transactional
    public InvestorPortfolioProjection getInvestorByName(String name) {
        return investorRepository.findByFullName(name).orElseThrow(()->new NotFoundException("Investor not found"));
    }

    /**
     * Delete investor by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteInvestor(String uid) {
        investorRepository.deleteByUid(uid);
    }

    /**
     * Get all equities of a company
     * @param investorUId
     * @return List<Equity>
     */
    @Override
    @Transactional
    public List<EquityProjection> getEquities(String investorUId) {
        return investorRepository.findByUid(investorUId).orElseThrow(() -> new NotFoundException("Investor not found")).getEquities();
    }

    /**
     * Save equity
     * @param equity
     */
    @Override
    @Transactional
    public void saveEquity(Equity equity) {
        Optional<Equity> userEquity = equityRepository.findById(equity.getId());
        if (userEquity == null) {
            equity.setId(0L);
        }
        equityRepository.save(equity);
    }

    /**
     * Get equity by uid
     * @param uid
     * @return equity
     */
    @Override
    @Transactional
    public EquityProjection getEquity(String uid) {
        Optional<EquityProjection> result = equityRepository.findByUid(uid);
        EquityProjection equity = null;
        if (result.isPresent()) {
            equity = result.get();
        }
        else {
            throw new NotFoundException("Did not find equity id: " + uid);
        }
        return equity;
    }

    /**
     * Delete equity by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteEquity(String uid) {
        equityRepository.deleteByUid(uid);
    }
}
