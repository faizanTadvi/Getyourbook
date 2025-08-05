package com.getyourbook.backend.controller;

import com.getyourbook.backend.dto.BookDto;
import com.getyourbook.backend.model.Book;
import com.getyourbook.backend.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Import List

/**
 * REST Controller for handling book-related API endpoints.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Endpoint for creating a new book listing.
     * Maps to POST /api/books
     */
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        try {
            Book newBook = bookService.createBook(bookDto);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the book.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for retrieving all book listings.
     * Maps to GET /api/books
     *
     * @return A ResponseEntity containing a list of all books.
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
