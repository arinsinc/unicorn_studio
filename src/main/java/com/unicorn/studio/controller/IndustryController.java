package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Industry;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.UtilityService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class IndustryController {
    @Autowired
    private UtilityService utilityService;

    @GetMapping("/industries")
    public ResponseEntity<ResponseSerializer> getIndustries() {
        List<Industry> industryList = utilityService.getIndustries();
        ResponseSerializer response = new ResponseSerializer(true,"success","All industries fetched successfully", industryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/industries/{industryId}")
    public ResponseEntity<ResponseSerializer> getIndustry(@PathVariable int industryId) {
        Industry industry = utilityService.getIndustry(industryId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Industry fetched successfully", industry);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/industries")
    public ResponseEntity<ResponseSerializer> addIndustry(@RequestBody Industry industry) {
        industry.setId((long)0);
        utilityService.saveIndustry(industry);
        ResponseSerializer response = new ResponseSerializer(true,"success","Industry saved successfully", industry);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/industries")
    public ResponseEntity<ResponseSerializer> updateIndustry(@RequestBody Industry industry) {
        utilityService.saveIndustry(industry);
        ResponseSerializer response = new ResponseSerializer(true,"success","Industry updated successfully", industry);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/industries/{industryId}")
    public ResponseEntity<ResponseSerializer> deleteIndustry(@PathVariable int industryId) {
        utilityService.deleteIndustry(industryId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Industry deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
