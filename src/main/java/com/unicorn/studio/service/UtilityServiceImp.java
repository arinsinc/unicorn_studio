package com.unicorn.studio.service;

import com.unicorn.studio.dao.IndustryRepository;
import com.unicorn.studio.entity.Industry;
import com.unicorn.studio.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityServiceImp implements UtilityService {
    @Autowired
    private IndustryRepository industryRepository;

    public UtilityServiceImp(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * Get all industries
     * @return List<Industry>
     */
    @Override
    @Transactional
    public List<Industry> getIndustries() {
        return industryRepository.findAll();
    }

    /**
     * Save industry
     * @param industry
     */
    @Override
    @Transactional
    public void saveIndustry(Industry industry) {
        industryRepository.save(industry);
    }

    /**
     * Get industry by id
     * @param id
     * @return industry
     */
    @Override
    @Transactional
    public Industry getIndustry(long id) {
        Optional<Industry> result = industryRepository.findById(id);
        Industry industry = null;
        if (result.isPresent()) {
            industry = result.get();
        }
        else {
            throw new NotFoundException("Did not find industry id: " + id);
        }
        return industry;
    }

    /**
     * Delete industry by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteIndustry(long id) {
        industryRepository.deleteById(id);
    }
}
