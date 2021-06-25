package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import com.unicorn.studio.projection.InvestorProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.InvestorService;
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
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    @GetMapping("/investors")
    public ResponseEntity<ResponseSerializer> getInvestors(@RequestParam int pageNo, Pageable pageable) {
        pageable = PageRequest.of(pageNo,20);
        List<InvestorPortfolioProjection> investorList = investorService.getInvestors(pageable);
        ResponseSerializer response = new ResponseSerializer(true,"success","All investors fetched successfully", investorList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/investors/{investorId}")
    public ResponseEntity<ResponseSerializer> getInvestor(@PathVariable String investorId) {
        InvestorProjection investor = investorService.getInvestor(investorId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Investor fetched successfully", investor);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/investors")
    public ResponseEntity<ResponseSerializer> addInvestor(@RequestBody Investor investor) {
        investor.setId((long)0);
        investorService.saveInvestor(investor);
        ResponseSerializer response = new ResponseSerializer(true,"success","Investor saved successfully", investor);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/investors")
    public ResponseEntity<ResponseSerializer> updateInvestor(@RequestBody Investor investor) {
        investorService.saveInvestor(investor);
        ResponseSerializer response = new ResponseSerializer(true,"success","Investor updated successfully", investor);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/investors/{investorId}")
    public ResponseEntity<ResponseSerializer> deleteInvestor(@PathVariable String investorId) {
        investorService.deleteInvestor(investorId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Investor deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
