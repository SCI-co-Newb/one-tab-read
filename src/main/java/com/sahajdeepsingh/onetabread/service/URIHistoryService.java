package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.URIHistory;
import com.sahajdeepsingh.onetabread.repository.URIHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class URIHistoryService {

    private final URIHistoryRepository uriHistoryRepository;

    public URIHistoryService(URIHistoryRepository uriHistoryRepository) {
        this.uriHistoryRepository = uriHistoryRepository;
    }

    // POST method
    public URIHistory save(URIHistory uriHistory) {
        return uriHistoryRepository.save(uriHistory);
    }

    // GET methods
    public List<URIHistory> getURIHistoryByBookId(Long book_id) {
        return uriHistoryRepository.findTop5ByBookIdOrderByVisitedAtDesc(book_id);
    }

    // DELETE method
    public void deleteById(Long id) {
        uriHistoryRepository.deleteById(id);
    }

    // PUT methods
    @Transactional
    public URIHistory updateURIHistory(Long book_id, URIHistory uriHistory) {
        URIHistory uriHistoryToUpdate = uriHistoryRepository.findById(uriHistory.getId()).orElse(null);
        if (uriHistoryToUpdate != null && uriHistoryToUpdate.getBook().getId().equals(book_id)) {
            uriHistoryToUpdate.setUri(uriHistory.getUri());
            uriHistoryToUpdate.setVisitedAt(uriHistory.getVisitedAt());
            return uriHistoryRepository.save(uriHistoryToUpdate);
        }
        return null;
    }   // probably limit to 6 and each new one basically will get the oldest in history and update with the newest one, newest one visitedAt will make it the latest
}
