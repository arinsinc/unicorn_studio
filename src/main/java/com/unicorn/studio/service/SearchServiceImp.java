package com.unicorn.studio.service;




import com.unicorn.studio.dao.CompanyRepository;
import com.unicorn.studio.dao.InvestorRepository;
import com.unicorn.studio.dao.StartupProgramRepository;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.specification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class SearchServiceImp implements SearchService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private StartupProgramRepository startupProgramRepository;

    @Autowired
    private LocationService locationService;

    /**
     * Get company search result page by specifications
     * @param spec
     * @param pageable
     * @return Page<Company>
     */
    @Override
    @Transactional
    public Page<Company> getCompanySearch(Specification spec, Pageable pageable) {
        return companyRepository.findAll(spec, pageable);
    }

    /**
     * Get investor search result page by specification
     * @param spec
     * @param pageable
     * @return Page<Investor>
     */
    @Override
    @Transactional
    public Page<Investor> getInvestorSearch(Specification spec, Pageable pageable) {
        return investorRepository.findAll(spec, pageable);
    }

    /**
     * Get startup program search result page by specification
     * @param spec
     * @param pageable
     * @return Page<StartupProgram>
     */
    @Override
    @Transactional
    public Page<StartupProgram> getStartupProgramSearch(Specification spec, Pageable pageable) {
        return startupProgramRepository.findAll(spec, pageable);
    }

    /**
     * Create CompanySpecification object from request object
     * @param keyword
     * @param filters
     * @return spec
     */
    @Override
    public CompanySpecification extractCompanyFilterValues(String keyword, Map<String, String> filters) {
        CompanySpecification spec = new CompanySpecification();
        long location_id;
        if (filters.containsKey("location")) {
            location_id = locationService.getCityByName(filters.get("location")).getId();
            filters.remove("location");
            filters.put("location_id", String.valueOf(location_id));
        }
        filters.remove("keyword");
        spec.add(new SearchCriteria("name", keyword, SearchOperation.MATCH));
        spec.add(new SearchCriteria("profile", keyword, SearchOperation.MATCH));
        for (Map.Entry<String,String> item : filters.entrySet()) {
            spec.add(new SearchCriteria(item.getKey(), item, SearchOperation.MATCH));
        }
        return spec;
    }

    /**
     * Create InvestorSpecification object from request object
     * @param keyword
     * @param filters
     * @return spec
     */
    @Override
    public InvestorSpecification extractInvestorFilterValues(String keyword, Map<String,String> filters) {
        InvestorSpecification spec = new InvestorSpecification();
        filters.remove("keyword");
        spec.add(new SearchCriteria("fullName", keyword, SearchOperation.MATCH));
        spec.add(new SearchCriteria("title", keyword, SearchOperation.MATCH));
        spec.add(new SearchCriteria("profile", keyword, SearchOperation.MATCH));
        for (Map.Entry<String,String> item : filters.entrySet()) {
            spec.add(new SearchCriteria(item.getKey(), item.getValue(), SearchOperation.MATCH));
        }
        return spec;
    }

    /**
     * Create StartupProgramSpecification object from request object
     * @param keyword
     * @param filters
     * @return spec
     */
    @Override
    public StartupProgramSpecification extractStartupProgramFilterValues(String keyword, Map<String,String> filters) {
        StartupProgramSpecification spec = new StartupProgramSpecification();
        filters.remove("keyword");
        spec.add(new SearchCriteria("name", keyword, SearchOperation.MATCH));
        spec.add(new SearchCriteria("profile", keyword, SearchOperation.MATCH));
        for (Map.Entry<String,String> item : filters.entrySet()) {
            spec.add(new SearchCriteria(item.getKey(), item.getValue(), SearchOperation.MATCH));
        }
        return spec;
    }
}
