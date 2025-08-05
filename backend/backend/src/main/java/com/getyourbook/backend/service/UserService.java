package com.getyourbook.backend.service;

import com.getyourbook.backend.dto.LoginRequest;
import com.getyourbook.backend.dto.LoginResponse;
import com.getyourbook.backend.dto.UserRegistrationDto;
import com.getyourbook.backend.model.User;
import com.getyourbook.backend.repository.UserRepository;
import com.getyourbook.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Inject the JwtUtil

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(UserRegistrationDto registrationDto) {
        // ... (registration logic remains the same)
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("A user with email " + registrationDto.getEmail() + " already exists.");
        }
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setContactDetails(registrationDto.getContactDetails());
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newUser.setPasswordHash(hashedPassword);
        return userRepository.save(newUser);
    }

    /**
     * Authenticates a user and generates a JWT.
     *
     * @param loginRequest The DTO containing the user's login credentials.
     * @return A LoginResponse containing the JWT and user data.
     */
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                // --- GENERATE TOKEN ---
                // Create a UserDetails object that JwtUtil can use
                final UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPasswordHash(), new ArrayList<>()
                );
                // Generate the token
                final String token = jwtUtil.generateToken(userDetails);

                // Return the new LoginResponse object
                return new LoginResponse(token, user);
            }
        }
        throw new BadCredentialsException("Invalid email or password.");
    }
}
