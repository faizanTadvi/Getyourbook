package com.getyourbook.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for handling user login requests.
 */
@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;

}
