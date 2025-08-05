package com.getyourbook.backend.repository;

import com.getyourbook.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Book entity.
 * This interface handles all database operations for Books.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // We can add custom query methods here in the future,
    // for example, to find books by title or author.
}

