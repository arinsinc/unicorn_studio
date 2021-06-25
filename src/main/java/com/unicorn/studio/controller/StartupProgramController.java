package com.unicorn.studio.controller;



import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.projection.StartupProgramProjection;
import com.unicorn.studio.service.StartupProgramService;
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
public class StartupProgramController {
    @Autowired
    private StartupProgramService startupProgramService;

    @GetMapping("/startup-programs")
    public ResponseEntity<ResponseSerializer> getStartupPrograms(@RequestParam int pageNo, Pageable pageable) {
        pageable = PageRequest.of(pageNo,20);
        List<StartupProgramProjection> startupPrograms = startupProgramService.getStartupPrograms(pageable);
        ResponseSerializer response = new ResponseSerializer(true,"success","Startup programs fetched successfully",startupPrograms);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/startup-programs/{programId}")
    public ResponseEntity<ResponseSerializer> getStartupProgram(@PathVariable String programId) {
        StartupProgramProjection startupProgram = startupProgramService.getStartupProgram(programId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Startup program fetched successfully",startupProgram);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/startup-programs")
    public ResponseEntity<ResponseSerializer> addStartupProgram(@RequestBody StartupProgram startupProgram) {
        startupProgram.setId(0);
        startupProgramService.saveStartupProgram(startupProgram);
        ResponseSerializer response = new ResponseSerializer(true,"success","Startup program added successfully",startupProgram);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/startup-programs")
    public ResponseEntity<ResponseSerializer> updateStartupProgram(@RequestBody StartupProgram startupProgram) {
        startupProgramService.saveStartupProgram(startupProgram);
        ResponseSerializer response = new ResponseSerializer(true,"success","Startup program updated successfully",startupProgram);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/startup-programs/{programId}")
    public ResponseEntity<ResponseSerializer> deleteStartupProgram(@PathVariable String programId) {
        startupProgramService.deleteStartupProgram(programId);
        ResponseSerializer response = new ResponseSerializer(true,"success","Startup program deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
