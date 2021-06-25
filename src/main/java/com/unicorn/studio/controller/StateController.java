package com.unicorn.studio.controller;



import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.State;
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
public class StateController {
    @Autowired
    private LocationService location;

    @GetMapping("/states")
    public ResponseEntity<ResponseSerializer> getStates() {
        List<State> stateList = location.getStates();
        ResponseSerializer response = new ResponseSerializer(true,"success","All states fetched successfully", stateList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/states?country_id={countryId}")
    public ResponseEntity<ResponseSerializer> getStatesByCountry(@RequestParam long countryId) {
        List<State> stateList = location.getStatesByCountry(countryId);
        ResponseSerializer response = new ResponseSerializer(true,"success","States fetched successfully", stateList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/states/{stateId}")
    public ResponseEntity<ResponseSerializer> getState(@PathVariable int stateId) {
        State state = location.getState(stateId);
        ResponseSerializer response = new ResponseSerializer(true,"success","State fetched successfully", state);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/states")
    public ResponseEntity<ResponseSerializer> addState(@RequestBody State state) {
        state.setId((long)0);
        location.saveState(state);
        ResponseSerializer response = new ResponseSerializer(true,"success","State saved successfully", state);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/states")
    public ResponseEntity<ResponseSerializer> updateState(@RequestBody State state) {
        location.saveState(state);
        ResponseSerializer response = new ResponseSerializer(true,"success","State updated successfully", state);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/states/{stateId}")
    public ResponseEntity<ResponseSerializer> deleteState(@PathVariable int stateId) {
        location.deleteState(stateId);
        ResponseSerializer response = new ResponseSerializer(true,"success","State deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
