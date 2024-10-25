package org.example.smartcabs.service.impl;

import org.example.smartcabs.config.JwtUtilities;
import org.example.smartcabs.config.SpringSecurityConfig;
import org.example.smartcabs.dto.AuthDto;
import org.example.smartcabs.dto.BookingRequest;
import org.example.smartcabs.dto.TripInfo;
import org.example.smartcabs.dto.UserAuthDto;
import org.example.smartcabs.model.User;
import org.example.smartcabs.repository.UserRepository;
import org.example.smartcabs.service.TripService;
import org.example.smartcabs.service.UserService;
import org.example.smartcabs.utilities.AppLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripService tripService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilities jwtUtilities;

    Logger logger = AppLogger.getLogger();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final PasswordEncoder passwordEncoder = new SpringSecurityConfig().passwordEncoder();

    @Override
    public ResponseEntity<?> authenticate(AuthDto authDto) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(),
                            authDto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Incorrect Username or password", HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> userFromDb = userRepository.findByEmail(authDto.getEmail());
        User user;
        if (userFromDb.isPresent()) {
            user = userFromDb.get();
        } else {
            logger.warn("Incorrect Username or password request found");
            return new ResponseEntity<>("Incorrect Username or password", HttpStatus.UNAUTHORIZED);
        }
        user.setRole("ROLE_USER");
        String token = jwtUtilities.generateToken(user.getUsername(), user.getRole());
        return new ResponseEntity<>(new UserAuthDto(token, "Bearer ", user.getUserId(), user.getRole()),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> register(User userinfo) {

        if (userRepository.existsByEmail(userinfo.getEmail())) {
            logger.warn("Request for already existing email{}", userinfo.getEmail());
            return new ResponseEntity<>("Try using different email. This email is already taken!", HttpStatus.CONFLICT);
        } else {
            User user = new User();
            user.setEmail(userinfo.getEmail());
            user.setName(userinfo.getName());
            user.setPassword(passwordEncoder.encode(userinfo.getPassword()));
            user.setRole("ROLE_USER");
            user.setMobileNumber(userinfo.getMobileNumber());
            userRepository.save(user);
            String token = jwtUtilities.generateToken(user.getEmail(), "ROLE_USER");
            return new ResponseEntity<>(new UserAuthDto(token, "Bearer ", user.getUserId(),
                            user.getRole()), HttpStatus.OK);
        }
    }

    @Override
    public TripInfo bookCab(BookingRequest bookingRequest) throws Exception {
        User user = findByUserId(bookingRequest.getUserId());
        return tripService.processTrip(bookingRequest, user);
    }

    private User findByUserId(Long userId)throws Exception{
        return userRepository.findById(userId).orElseThrow(() -> new Exception("User not found."));
    }
}
