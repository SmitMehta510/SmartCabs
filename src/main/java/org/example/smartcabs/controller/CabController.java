package org.example.smartcabs.controller;


import org.example.smartcabs.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cab")
public class CabController {

    @Autowired
    CabService cabService;

    @GetMapping("update-location")
    public ResponseEntity<?> getCabLocationUpdate(){
        return new ResponseEntity<>(cabService.getCabLocation(), HttpStatus.OK);
    }
}
