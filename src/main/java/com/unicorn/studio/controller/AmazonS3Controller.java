package com.unicorn.studio.controller;



import com.unicorn.studio.service.AmazonS3ClientService;
import com.unicorn.studio.service.UserService;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class AmazonS3Controller {
    @Autowired
    private AmazonS3ClientService amazonS3;

    @Autowired
    private UserService userService;

    @PostMapping("/images")
    public ResponseEntity<ResponseSerializer> uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam String fileType) {
        String fileName = amazonS3.uploadFile(file,fileType);
        ResponseSerializer response = new ResponseSerializer(true,"success","Image uploaded successfully",fileName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/images")
    public ResponseEntity<ResponseSerializer> deleteFile(@RequestParam String fileUrl) {
        amazonS3.deleteFile(fileUrl,"delete");
        ResponseSerializer response = new ResponseSerializer(true,"success","Image deleted successfully",null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
