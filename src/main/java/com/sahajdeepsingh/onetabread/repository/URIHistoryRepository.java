package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.URIHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URIHistoryRepository extends JpaRepository<URIHistory, Long> {
    List<URIHistory> findTop5ByBookIdOrderByVisitedAtDesc(Long book_id);
}
