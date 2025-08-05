package com.getyourbook.backend.service;

import com.getyourbook.backend.dto.BookDto;
import com.getyourbook.backend.model.Book;
import com.getyourbook.backend.model.User;
import com.getyourbook.backend.repository.BookRepository;
import com.getyourbook.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // Import List

/**
 * Service class for handling book-related business logic.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new book listing.
     */
    public Book createBook(BookDto bookDto) {
        User user = userRepository.findById(bookDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + bookDto.getUserId()));

        Book newBook = new Book();
        newBook.setTitle(bookDto.getTitle());
        newBook.setAuthor(bookDto.getAuthor());
        newBook.setIsbn(bookDto.getIsbn());
        newBook.setDescription(bookDto.getDescription());
        newBook.setBookCondition(bookDto.getBookCondition());
        newBook.setListingType(bookDto.getListingType());
        newBook.setPrice(bookDto.getPrice());
        newBook.setUser(user);

        return bookRepository.save(newBook);
    }

    /**
     * Retrieves all book listings from the database.
     *
     * @return A list of all Book entities.
     */
    public List<Book> getAllBooks() {
        // Use the repository's findAll() method to get all books.
        return bookRepository.findAll();
    }
}
