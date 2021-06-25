package com.unicorn.studio.controller;



import com.unicorn.studio.entity.CompanyMetrics;
import com.unicorn.studio.projection.CompanyMetricsProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class CompanyMetricsController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/company-metrics")
    public ResponseEntity<ResponseSerializer> getAllCompanyMetricsByCompany(@RequestParam String companyId) {
        List<CompanyMetricsProjection> companyMetricsList = companyService.getAllCompanyMetrics(companyId);
        ResponseSerializer response = new ResponseSerializer(true,"success","All company metrics fetched successfully", companyMetricsList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/company-metrics/{companyMetricsId}")
    public ResponseEntity<ResponseSerializer> getCompanyMetrics(@PathVariable String companyMetricsId) {
        CompanyMetricsProjection companyMetrics = companyService.getCompanyMetrics(companyMetricsId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company metrics fetched successfully", companyMetrics);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/company-metrics")
    public ResponseEntity<ResponseSerializer> addCompanyMetrics(@RequestBody CompanyMetrics companyMetrics) {
        companyMetrics.setId((long)0);
        companyService.saveCompanyMetrics(companyMetrics);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company metrics added successfully", companyMetrics);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/company-metrics")
    public ResponseEntity<ResponseSerializer> updateCompanyMetrics(@RequestBody CompanyMetrics companyMetrics) {
        companyService.saveCompanyMetrics(companyMetrics);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company metrics updated successfully", companyMetrics);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/company-metrics/{companyMetricsId}")
    public ResponseEntity<ResponseSerializer> deleteCompanyMetrics(@PathVariable String companyMetricsId) {
        companyService.deleteCompanyMetrics(companyMetricsId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Company metrics deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

