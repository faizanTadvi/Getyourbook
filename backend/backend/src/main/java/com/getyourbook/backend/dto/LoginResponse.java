package com.getyourbook.backend.dto;

import com.getyourbook.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for sending a JWT and user data back after a successful login.
 */
@Getter
@Setter
@AllArgsConstructor // Lombok annotation to create a constructor with all fields
public class LoginResponse {

    private String token;
    private User user;

}
