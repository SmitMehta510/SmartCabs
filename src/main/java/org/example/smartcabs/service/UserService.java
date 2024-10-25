package org.example.smartcabs.service;

import org.example.smartcabs.dto.AuthDto;
import org.example.smartcabs.dto.BookingRequest;
import org.example.smartcabs.dto.TripInfo;
import org.example.smartcabs.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> authenticate(AuthDto authDto);
    ResponseEntity<?> register (User user);
    TripInfo bookCab(BookingRequest bookingRequest) throws Exception;
}
