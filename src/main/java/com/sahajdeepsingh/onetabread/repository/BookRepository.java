package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitleAndUserId(String title, Long user_id);
}
