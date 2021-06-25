package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Company;
import com.unicorn.studio.projection.CompanyPortfolioProjection;
import com.unicorn.studio.projection.CompanyProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public ResponseEntity<ResponseSerializer> getCompanies(@RequestParam int pageNo, Pageable pageable) {
        pageable = PageRequest.of(pageNo,20);
        List<CompanyPortfolioProjection> organizationList = companyService.getCompanies(pageable);
        ResponseSerializer response = new ResponseSerializer(true,"success","Companies fetched successfully",organizationList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<ResponseSerializer> getCompany(@PathVariable String companyId) {
        CompanyProjection company = companyService.getCompany(companyId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company fetched successfully",company);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/companies")
    public ResponseEntity<ResponseSerializer> addCompany(@RequestBody Company company) {
        company.setId((long)0);
        companyService.saveCompany(company);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company added successfully", company);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/companies")
    public ResponseEntity<ResponseSerializer> updateCompany(@RequestBody Company company) {
        companyService.saveCompany(company);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company updated successfully", company);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/companies/{companyId}")
    public ResponseEntity<ResponseSerializer> deleteCompany(@PathVariable String companyId) {
        companyService.deleteCompany(companyId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
