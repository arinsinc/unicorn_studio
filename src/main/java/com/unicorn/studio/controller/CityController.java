package com.unicorn.studio.controller;



import com.unicorn.studio.entity.City;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.LocationService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class CityController {
    @Autowired
    private LocationService location;

    @GetMapping("/cities")
    public ResponseEntity<ResponseSerializer> getCities() {
        List<City> cityList = location.getCities();
        ResponseSerializer response = new ResponseSerializer(true,"success","All cities", cityList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/cities?stateId={stateId}")
    public ResponseEntity<ResponseSerializer> getCitiesByState(@RequestParam long stateId) {
        List<City> cityList = location.getCitiesByState(stateId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Cities for State Id:"+stateId, cityList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<ResponseSerializer> getCity(@PathVariable int cityId) {
        City city = location.getCity(cityId);
        if (city == null) {
            ResponseSerializer response = new ResponseSerializer(true,"error","City not found",null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        ResponseSerializer response = new ResponseSerializer(true,"success","City with Id:"+cityId,city);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<ResponseSerializer> addCity(@RequestBody City city) {
        city.setId((long)0);
        if (city == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","Invalid request data",null);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        location.saveCity(city);
        ResponseSerializer response = new ResponseSerializer(true,"success","City saved successfully",null);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/cities")
    public ResponseEntity<ResponseSerializer> updateCity(@RequestBody City city) {
        if (city == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","Invalid request data",null);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        location.saveCity(city);
        ResponseSerializer response = new ResponseSerializer(true,"success","City updated successfully",city);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/cities/{cityId}")
    public ResponseEntity<ResponseSerializer> deleteCity(@PathVariable int cityId) {
        City isCity = location.getCity(cityId);
        if (isCity == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","City not found",null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        location.deleteCity(cityId);
        ResponseSerializer response = new ResponseSerializer(true,"success","City deleted successfully",null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
