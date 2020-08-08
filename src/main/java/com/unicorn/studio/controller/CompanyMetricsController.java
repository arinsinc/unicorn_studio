package com.unicorn.studio.controller;

import com.unicorn.studio.entity.CompanyMetrics;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.UnicornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyMetricsController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/company-metrics")
    public List<CompanyMetrics> getCompanies() {
        return unicornService.getCompanyMetrics();
    }

    @GetMapping("/companyMetrics/{companyMetricsId}")
    public CompanyMetrics getCompanyMetrics(@PathVariable int companyMetricsId) {
        CompanyMetrics companyMetrics = unicornService.getCompanyMetrics(companyMetricsId);
        if (companyMetrics == null) {
            throw new NotFoundException("CompanyMetrics not found with ID:" + companyMetricsId);
        }
        return companyMetrics;
    }

    @PostMapping("/company-metrics")
    public CompanyMetrics addCompanyMetrics(@RequestBody CompanyMetrics companyMetrics) {
        companyMetrics.setId((long)0);
        unicornService.saveCompanyMetrics(companyMetrics);
        return companyMetrics;
    }

    @PutMapping("/company-metrics")
    public CompanyMetrics updateCompanyMetrics(@RequestBody CompanyMetrics companyMetrics) {
        unicornService.saveCompanyMetrics(companyMetrics);
        return companyMetrics;
    }

    @DeleteMapping("/company-metrics/{companyMetricsId}")
    public String deleteCompanyMetrics(@PathVariable int companyMetricsId) {
        CompanyMetrics isCompanyMetrics = unicornService.getCompanyMetrics(companyMetricsId);
        if (isCompanyMetrics == null) {
            throw new NotFoundException("CompanyMetrics not found with ID:" + companyMetricsId);
        }
        unicornService.deleteCompanyMetrics(companyMetricsId);
        return "CompanyMetrics deleted successfully for Id:" + companyMetricsId;
    }
}
