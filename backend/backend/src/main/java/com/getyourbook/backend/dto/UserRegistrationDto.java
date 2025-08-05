package com.getyourbook.backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * A Data Transfer Object for handling user registration data.
 * This object is used to transfer data from the controller to the service layer.
 */
@Getter
@Setter
public class UserRegistrationDto {

    private String username;
    private String email;
    private String password;
    private String contactDetails;

}
