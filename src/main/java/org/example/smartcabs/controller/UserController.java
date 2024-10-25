package org.example.smartcabs.controller;

import org.example.smartcabs.dto.AuthDto;
import org.example.smartcabs.dto.BookingRequest;
import org.example.smartcabs.dto.TripInfo;
import org.example.smartcabs.model.User;
import org.example.smartcabs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/check")
    public String check(){
        return "Welcome to SmartCabs";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthDto authDto) {
        return userService.authenticate(authDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/book-cab")
    public ResponseEntity<?> bookCab(@RequestBody BookingRequest bookingRequest) throws Exception {
        TripInfo trip = userService.bookCab(bookingRequest);
        if (trip != null) {
            return new ResponseEntity<>(trip, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
