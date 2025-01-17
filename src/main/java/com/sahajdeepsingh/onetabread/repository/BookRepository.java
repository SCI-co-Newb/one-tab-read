package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndUserId(String title, Long user_id);
}
