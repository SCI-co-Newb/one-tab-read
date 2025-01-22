package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIdAndUserId(Long id, Long user_id);    // no find by title since it is not unique

    List<Book> findAllByUserId(Long user_id);
}
