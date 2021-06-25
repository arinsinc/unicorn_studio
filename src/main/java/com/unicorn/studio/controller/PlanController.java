package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Plan;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.PlanProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.SubscriptionService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class PlanController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/plans")
    public ResponseEntity<ResponseSerializer> getPlans() {
        List<PlanProjection> planList = subscriptionService.getPlans();
        ResponseSerializer response = new ResponseSerializer(true,"success","All plans fetched successfully", planList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/plans/{planId}")
    public ResponseEntity<ResponseSerializer> getPlan(@PathVariable String planId) {
        PlanProjection plan = subscriptionService.getPlan(planId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Plan fetched successfully", plan);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/plans")
    public ResponseEntity<ResponseSerializer> addPlan(@RequestBody Plan plan) {
        plan.setId((long)0);
        subscriptionService.savePlan(plan);
        ResponseSerializer response = new ResponseSerializer(true,"success","Plan saved successfully", plan);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/plans")
    public ResponseEntity<ResponseSerializer> updatePlan(@RequestBody Plan plan) {
        subscriptionService.savePlan(plan);
        ResponseSerializer response = new ResponseSerializer(true,"success","Plan updated successfully", plan);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<ResponseSerializer> deletePlan(@PathVariable String planId) {
        subscriptionService.deletePlan(planId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Plan deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
