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

import com.unicorn.studio.entity.Investment;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
public class InvestmentController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/investments")
    public List<Investment> getInvestments() {
        return unicornService.getInvestments();
    }

    @GetMapping("/investment/{investmentId}")
    public Investment getInvestment(@PathVariable int investmentId) {
        Investment investment = unicornService.getInvestment(investmentId);
        if (investment == null) {
            throw new NotFoundException("Investment not found with ID:" + investmentId);
        }
        return investment;
    }

    @PostMapping("/investments")
    public Investment addInvestment(@RequestBody Investment investment) {
        investment.setId((long)0);
        unicornService.saveInvestment(investment);
        return investment;
    }

    @PutMapping("/investments")
    public Investment updateInvestment(@RequestBody Investment investment) {
        unicornService.saveInvestment(investment);
        return investment;
    }

    @DeleteMapping("/investments/{investmentId}")
    public String deleteInvestment(@PathVariable int investmentId) {
        Investment isInvestment = unicornService.getInvestment(investmentId);
        if (isInvestment == null) {
            throw new NotFoundException("Investment not found with ID:" + investmentId);
        }
        unicornService.deleteInvestment(investmentId);
        return "Investment deleted successfully for Id:" + investmentId;
    }
}
