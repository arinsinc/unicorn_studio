package com.unicorn.studio.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.dao.*;
import com.unicorn.studio.entity.*;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImp implements CompanyService{
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMetricsRepository companyMetricsRepository;

    @Autowired
    private FundingRepository fundingRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UtilityService utilityService;

    public CompanyServiceImp(CompanyRepository companyRepository, CompanyMetricsRepository companyMetricsRepository,
                             FundingRepository fundingRepository) {
        this.companyRepository = companyRepository;
        this.companyMetricsRepository = companyMetricsRepository;
        this.fundingRepository = fundingRepository;
    }

    /**
     * Get all companies
     * @return List<Company>
     */
    @Override
    @Transactional
    public List<CompanyPortfolioProjection> getCompanies(Pageable pageable) {
        return companyRepository.findAllCompanies(pageable);
    }

    /**
     * Save company
     * @param company
     */
    @Override
    @Transactional
    public void saveCompany(Company company) {
        Company userCompany = companyRepository.findByName(company.getName()).get(0);
        if (userCompany == null) {
            company.setId(0L);
            companyRepository.save(company);
        }
        else {
            if (!userCompany.getName().equals(company.getName()) ||
                    !userCompany.getProfile().equals(company.getProfile()) ||
                    !userCompany.getUrl().equals(company.getUrl()) ||
                    !userCompany.getFoundedYear().equals(company.getFoundedYear()) ||
                    !userCompany.getHeadquarter().equals(company.getHeadquarter()) ||
                    !userCompany.getIndustry().equals(company.getIndustry()) ||
                    !userCompany.getOrgType().equals(company.getOrgType()) ||
                    !userCompany.getSize().equals(company.getSize())) {
                userCompany.setName(company.getName());
                userCompany.setProfile(company.getProfile());
                userCompany.setUrl(company.getUrl());
                userCompany.setFoundedYear(company.getFoundedYear());
                userCompany.setHeadquarter(company.getHeadquarter());
                userCompany.setIndustry(company.getIndustry());
                userCompany.setOrgType(company.getOrgType());
                userCompany.setSize(company.getSize());
                companyRepository.save(userCompany);
            }
        }
    }

    /**
     * Get company by uid
     * @param uid
     * @return company
     */
    @Override
    @Transactional
    public CompanyProjection getCompany(String uid) {
        Optional<CompanyProjection> result = companyRepository.findByUid(uid);
        CompanyProjection company = null;
        if (result.isPresent()) {
            company = result.get();
        }
        else {
            throw new NotFoundException("Did not find company id: " + uid);
        }
        return company;
    }

    /**
     * Get company by name
     * @param name
     * @return Company
     */
    @Override
    @Transactional
    public CompanyPortfolioProjection getCompanyByName(String name) {
        List<CompanyPortfolioProjection> company = companyRepository.findCompanyByName(name);
        if (company.isEmpty()) {
            throw new NotFoundException("Did not find company: " + name);
        }
        return company.get(0);
    }

    /**
     * Create Company object from request object
     * @param obj
     * @return company
     */
    @Override
    public Company formatCompany(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap map = (LinkedHashMap) ((LinkedHashMap)((LinkedHashMap) obj).get("company")).get("industry");
        Industry industry = mapper.convertValue(map, Industry.class);
        industry = utilityService.getIndustry(industry.getId());
        map = (LinkedHashMap) ((LinkedHashMap) obj).get("company");
        Company company = mapper.convertValue(map,Company.class);
        company.setIndustry(industry);
        return company;
    }

    /**
     * Delete company by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteCompany(String uid) {
        companyRepository.deleteByUid(uid);
    }

    /**
     * Get all company metrics of a company
     * @param companyUId
     * @return List<CompanyMetrics>
     */
    @Override
    @Transactional
    public List<CompanyMetricsProjection> getAllCompanyMetrics(String companyUId) {
        return companyRepository.findByUid(companyUId).orElseThrow(() -> new NotFoundException("Company not found")).getCompanyMetrics();
    }

    /**
     * Save company metrics
     * @param companyMetrics
     */
    @Override
    @Transactional
    public void saveCompanyMetrics(CompanyMetrics companyMetrics) {
        Optional<CompanyMetrics> userCompanyMetrics = companyMetricsRepository.findById(companyMetrics.getId());
        if (userCompanyMetrics == null) {
            companyMetrics.setId(0L);
        }
        companyMetricsRepository.save(companyMetrics);
    }

    /**
     * Get company metrics by uid
     * @param uid
     * @return companyMetrics
     */
    @Override
    @Transactional
    public CompanyMetricsProjection getCompanyMetrics(String uid) {
        Optional<CompanyMetricsProjection> result = companyMetricsRepository.findByUid(uid);
        CompanyMetricsProjection companyMetrics = null;
        if (result.isPresent()) {
            companyMetrics = result.get();
        }
        else {
            throw new NotFoundException("Did not find company metrics id: " + uid);
        }
        return companyMetrics;
    }

    /**
     * Delete company metrics by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteCompanyMetrics(String uid) {
        companyMetricsRepository.deleteByUid(uid);
    }

    /**
     * Get all funding of a company
     * @param companyId
     * @return List<Funding>
     */
    @Override
    @Transactional
    public List<FundingProjection> getFundingList(String companyId) {
        return companyRepository.findByUid(companyId).orElseThrow(()->new NotFoundException("Company not found")).getFunding();
    }

    /**
     * Save funding
     * @param funding
     */
    @Override
    @Transactional
    public void saveFunding(Funding funding) {
        Optional<Funding> userFunding = fundingRepository.findById(funding.getId());
        if (userFunding == null) {
            funding.setId(0L);
        }
        fundingRepository.save(funding);
    }

    /**
     * Get funding by id
     * @param uid
     * @return funding
     */
    @Override
    @Transactional
    public FundingProjection getFunding(String uid) {
        Optional<FundingProjection> result = fundingRepository.findByUid(uid);
        FundingProjection funding = null;
        if (result.isPresent()) {
            funding = result.get();
        }
        else {
            throw new NotFoundException("Did not find funding id: " + uid);
        }
        return funding;
    }

    /**
     * Delete funding by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteFunding(String uid) {
        fundingRepository.deleteByUid(uid);
    }
}
