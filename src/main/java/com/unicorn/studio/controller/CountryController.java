package com.unicorn.studio.controller;



import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.Country;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.LocationService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class CountryController {
    @Autowired
    private LocationService location;

    @GetMapping("/countries")
    public ResponseEntity<ResponseSerializer> getCountries() {
        List<Country> countryList = location.getCountries();
        ResponseSerializer response = new ResponseSerializer(true,"success","Countries fetched successfully", countryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/countries/{countryId}")
    public ResponseEntity<ResponseSerializer> getCountry(@PathVariable int countryId) {
        Country country = location.getCountry(countryId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Country fetched successfully", country);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/countries")
    public ResponseEntity<ResponseSerializer> addCountry(@RequestBody Country country) {
        country.setId((long)0);
        location.saveCountry(country);
        ResponseSerializer response = new ResponseSerializer(true,"success","Country added successfully", country);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/countries")
    public ResponseEntity<ResponseSerializer> updateCountry(@RequestBody Country country) {
        location.saveCountry(country);
        ResponseSerializer response = new ResponseSerializer(true,"success","Country updated successfully", country);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/countries/{countryId}")
    public ResponseEntity<ResponseSerializer> deleteCountry(@PathVariable int countryId) {
        Country isCountry = location.getCountry(countryId);
        location.deleteCountry(countryId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Country deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
