package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.URIHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URIHistoryRepository extends JpaRepository<URIHistory, Long> {
    Optional<URIHistory> findByUriAndBookId(String uri, Long book_id);
}
