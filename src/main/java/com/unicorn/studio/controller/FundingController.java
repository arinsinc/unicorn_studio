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

import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
public class FundingController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/fundings")
    public List<Funding> getFundings() {
        return unicornService.getFundings();
    }

    @GetMapping("/funding/{fundingId}")
    public Funding getFunding(@PathVariable int fundingId) {
        Funding funding = unicornService.getFunding(fundingId);
        if (funding == null) {
            throw new NotFoundException("Funding not found with ID:" + fundingId);
        }
        return funding;
    }

    @PostMapping("/fundings")
    public Funding addFunding(@RequestBody Funding funding) {
        funding.setId((long)0);
        unicornService.saveFunding(funding);
        return funding;
    }

    @PutMapping("/fundings")
    public Funding updateFunding(@RequestBody Funding funding) {
        unicornService.saveFunding(funding);
        return funding;
    }

    @DeleteMapping("/fundings/{fundingId}")
    public String deleteFunding(@PathVariable int fundingId) {
        Funding isFunding = unicornService.getFunding(fundingId);
        if (isFunding == null) {
            throw new NotFoundException("Funding not found with ID:" + fundingId);
        }
        unicornService.deleteFunding(fundingId);
        return "Funding deleted successfully for Id:" + fundingId;
    }
}
