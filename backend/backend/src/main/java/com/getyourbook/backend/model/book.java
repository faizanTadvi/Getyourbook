package com.getyourbook.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Represents a Book entity.
 * This class is mapped to the "Books" table in the database.
 */
@Entity
@Table(name = "Books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String isbn;

    @Lob // Specifies that this should be a large text object
    private String description;

    @Column(name = "book_condition", nullable = false)
    private String bookCondition;

    @Column(name = "listing_type", nullable = false)
    private String listingType; // "Sell" or "Donate"

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String status = "Available"; // Default status

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    // --- Relationships ---

    @ManyToOne(fetch = FetchType.LAZY) // Defines a many-to-one relationship
    @JoinColumn(name = "user_id", nullable = false) // Specifies the foreign key column
    private User user;

}
