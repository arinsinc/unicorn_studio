package com.unicorn.studio.controller;

import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.UnicornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class InvestorController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/investors")
    public List<Investor> getInvestors() {
        return unicornService.getInvestors();
    }

    @GetMapping("/investor/{investorId}")
    public Investor getInvestor(@PathVariable int investorId) {
        Investor investor = unicornService.getInvestor(investorId);
        if (investor == null) {
            throw new NotFoundException("Investor not found with ID:" + investorId);
        }
        return investor;
    }

    @PostMapping("/investors")
    public Investor addInvestor(@RequestBody Investor investor) {
        investor.setId((long)0);
        unicornService.saveInvestor(investor);
        return investor;
    }

    @PutMapping("/investors")
    public Investor updateInvestor(@RequestBody Investor investor) {
        unicornService.saveInvestor(investor);
        return investor;
    }

    @DeleteMapping("/investors/{investorId}")
    public String deleteInvestor(@PathVariable int investorId) {
        Investor isInvestor = unicornService.getInvestor(investorId);
        if (isInvestor == null) {
            throw new NotFoundException("Investor not found with ID:" + investorId);
        }
        unicornService.deleteInvestor(investorId);
        return "Investor deleted successfully for Id:" + investorId;
    }
}
