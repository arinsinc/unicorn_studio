package com.unicorn.studio.service;

import com.unicorn.studio.entity.Industry;

import java.util.List;

public interface UtilityService {
    List<Industry> getIndustries();
    void saveIndustry(Industry industry);
    Industry getIndustry(long id);
    void deleteIndustry(long id);
}
