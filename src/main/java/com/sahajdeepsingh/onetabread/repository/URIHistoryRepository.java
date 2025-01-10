package com.sahajdeepsingh.onetabread.repository;

import com.sahajdeepsingh.onetabread.model.URIHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URIHistoryRepository extends JpaRepository<URIHistory, String> {
}
