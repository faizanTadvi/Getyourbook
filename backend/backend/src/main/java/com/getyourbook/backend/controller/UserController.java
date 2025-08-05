package com.getyourbook.backend.controller;

import com.getyourbook.backend.dto.LoginRequest;
import com.getyourbook.backend.dto.LoginResponse; // Import the new DTO
import com.getyourbook.backend.dto.UserRegistrationDto;
import com.getyourbook.backend.model.User;
import com.getyourbook.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User newUser = userService.registerUser(registrationDto);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for authenticating a user.
     * Now returns a LoginResponse containing the token and user data.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // The service now returns a LoginResponse object
            LoginResponse loginResponse = userService.loginUser(loginRequest);
            // Return the entire response object with a 200 OK status
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // If credentials are bad, return an error message with a 401 Unauthorized status
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
