package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.SubscriptionProjection;
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
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    public ResponseEntity<ResponseSerializer> getSubscriptions() {
        List<SubscriptionProjection> subscriptionList = subscriptionService.getSubscriptions();
        ResponseSerializer response = new ResponseSerializer(true,"success","All subscriptions fetched successfully", subscriptionList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<ResponseSerializer> getSubscription(@PathVariable String subscriptionId) {
        SubscriptionProjection subscription = subscriptionService.getSubscription(subscriptionId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Subscription fetched successfully", subscription);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<ResponseSerializer> addSubscription(@RequestBody Subscription subscription) {
        subscription.setId((long)0);
        subscriptionService.saveSubscription(subscription);
        ResponseSerializer response = new ResponseSerializer(true,"success","Subscription added successfully", subscription);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/subscriptions")
    public ResponseEntity<ResponseSerializer> updateSubscription(@RequestBody Subscription subscription) {
        subscriptionService.saveSubscription(subscription);
        ResponseSerializer response = new ResponseSerializer(true,"success","Subscription updated successfully", subscription);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<ResponseSerializer> deleteSubscription(@PathVariable String subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Subscription deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
