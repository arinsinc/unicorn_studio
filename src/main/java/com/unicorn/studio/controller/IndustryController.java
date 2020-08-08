package com.unicorn.studio.controller;

import com.unicorn.studio.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.studio.entity.Industry;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
public class IndustryController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/industry")
    public List<Industry> getIndustries() {
        return unicornService.getIndustries();
    }

    @GetMapping("/industry/{industryId}")
    public Industry getIndustry(@PathVariable int industryId) {
        Industry industry = unicornService.getIndustry(industryId);
        if (industry == null) {
            throw new NotFoundException("Industry not found with ID:" + industryId);
        }
        return industry;
    }

    @PostMapping("/industry")
    public Industry addIndustry(@RequestBody Industry industry) {
        industry.setId((long)0);
        unicornService.saveIndustry(industry);
        return industry;
    }

    @PutMapping("/industry")
    public Industry updateIndustry(@RequestBody Industry industry) {
        unicornService.saveIndustry(industry);
        return industry;
    }

    @DeleteMapping("/industry/{industryId}")
    public String deleteIndustry(@PathVariable int industryId) {
        Industry isIndustry = unicornService.getIndustry(industryId);
        if (isIndustry == null) {
            throw new NotFoundException("Industry not found with ID:" + industryId);
        }
        unicornService.deleteIndustry(industryId);
        return "Industry deleted successfully for Id:" + industryId;
    }
}
