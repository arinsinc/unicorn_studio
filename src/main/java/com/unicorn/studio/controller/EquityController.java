package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.projection.EquityProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.InvestorService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class EquityController {
    @Autowired
    private InvestorService investorService;

    @GetMapping("/equities")
    public ResponseEntity<ResponseSerializer> getAllEquitiesByInvestor(@RequestParam String investorId) {
        List<EquityProjection> equityList = investorService.getEquities(investorId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Equities fetched successfully", equityList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/equities/{equityId}")
    public ResponseEntity<ResponseSerializer> getEquity(@PathVariable String equityId) {
        EquityProjection equity = investorService.getEquity(equityId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Equity fetched successfully", equity);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/equities")
    public ResponseEntity<ResponseSerializer> addEquity(@RequestBody Equity equity) {
        equity.setId((long)0);
        investorService.saveEquity(equity);
        ResponseSerializer response = new ResponseSerializer(true,"success","Equity added successfully", equity);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/equities")
    public ResponseEntity<ResponseSerializer> updateEquity(@RequestBody Equity equity) {
        investorService.saveEquity(equity);
        ResponseSerializer response = new ResponseSerializer(true,"success","Equity updated successfully", equity);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/equities/{equityId}")
    public ResponseEntity<ResponseSerializer> deleteEquity(@PathVariable String equityId) {
        investorService.deleteEquity(equityId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Equity deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
