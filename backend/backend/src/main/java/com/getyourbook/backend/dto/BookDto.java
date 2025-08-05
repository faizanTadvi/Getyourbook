package com.getyourbook.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * A DTO for creating and updating book listings.
 */
@Getter
@Setter
public class BookDto {

    private String title;
    private String author;
    private String isbn;
    private String description;
    private String bookCondition;
    private String listingType; // "Sell" or "Donate"
    private BigDecimal price;

    // We need to know which user is listing the book.
    private Long userId;

}

