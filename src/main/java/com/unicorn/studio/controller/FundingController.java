package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.projection.FundingProjection;
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
public class FundingController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/funding")
    public ResponseEntity<ResponseSerializer> getAllFundingByCompany(@RequestParam String companyId) {
        List<FundingProjection> fundingList = companyService.getFundingList(companyId);
        ResponseSerializer response = new ResponseSerializer(true,"success","All funding fetched successfully", fundingList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/funding/{fundingId}")
    public ResponseEntity<ResponseSerializer> getFunding(@PathVariable String fundingId) {
        FundingProjection funding = companyService.getFunding(fundingId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Funding fetched successfully", funding);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/funding")
    public ResponseEntity<ResponseSerializer> addFunding(@RequestBody Funding funding) {
        funding.setId((long)0);
        companyService.saveFunding(funding);
        ResponseSerializer response = new ResponseSerializer(true,"success","Funding saved successfully", funding);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/funding")
    public ResponseEntity<ResponseSerializer> updateFunding(@RequestBody Funding funding) {
        companyService.saveFunding(funding);
        ResponseSerializer response = new ResponseSerializer(true,"success","Funding updated successfully", funding);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/funding/{fundingId}")
    public ResponseEntity<ResponseSerializer> deleteFunding(@PathVariable String fundingId) {
        companyService.deleteFunding(fundingId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Funding deleted successsfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
