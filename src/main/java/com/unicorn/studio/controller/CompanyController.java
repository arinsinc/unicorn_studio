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

import com.unicorn.studio.entity.Company;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return unicornService.getCompanies();
    }

    @GetMapping("/company/{companyId}")
    public Company getCompany(@PathVariable int companyId) {
        Company company = unicornService.getCompany(companyId);
        if (company == null) {
            throw new NotFoundException("Company not found with ID:" + companyId);
        }
        return company;
    }

    @PostMapping("/companies")
    public Company addCompany(@RequestBody Company company) {
        company.setId((long)0);
        unicornService.saveCompany(company);
        return company;
    }

    @PutMapping("/companies")
    public Company updateCompany(@RequestBody Company company) {
        unicornService.saveCompany(company);
        return company;
    }

    @DeleteMapping("/companies/{companyId}")
    public String deleteCompany(@PathVariable int companyId) {
        Company isCompany = unicornService.getCompany(companyId);
        if (isCompany == null) {
            throw new NotFoundException("Company not found with ID:" + companyId);
        }
        unicornService.deleteCompany(companyId);
        return "Company deleted successfully for Id:" + companyId;
    }
    
}
