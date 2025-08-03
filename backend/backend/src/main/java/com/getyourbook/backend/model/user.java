package com.getyourbook.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * Represents a User entity.
 * This class is mapped to the "Users" table in the database.
 */
@Entity
@Table(name = "Users") // Explicitly maps this class to the "Users" table
@Getter // Lombok annotation to generate all getter methods
@Setter // Lombok annotation to generate all setter methods
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the ID to be auto-incremented by the database
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "contact_details", nullable = false)
    private String contactDetails;

    @CreationTimestamp // Automatically sets the value on creation
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

}

