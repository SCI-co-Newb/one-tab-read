package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.URIHistory;
import com.sahajdeepsingh.onetabread.repository.URIHistoryRepository;
import org.springframework.stereotype.Service;

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
    public URIHistory findById(Long id) {
        return uriHistoryRepository.findById(id).orElse(null);
    }

    public URIHistory findByUriAndBookId(String uri, Long book_id) {
        return uriHistoryRepository.findByUriAndBookId(uri, book_id).orElse(null);
    }

    // DELETE method
    public void deleteById(Long id) {
        uriHistoryRepository.deleteById(id);
    }

    // PUT methods
    public URIHistory updateURIHistory(URIHistory uriHistory) {
        URIHistory uriHistoryToUpdate = uriHistoryRepository.findById(uriHistory.getId()).orElse(null);
        if (uriHistoryToUpdate != null) {
            uriHistoryToUpdate.setUri(uriHistory.getUri());
            uriHistoryToUpdate.setBook(uriHistory.getBook());
            uriHistoryToUpdate.setVisitedAt(uriHistory.getVisitedAt());
            return uriHistoryRepository.save(uriHistoryToUpdate);
        }
        return null;
    }   // probably limit to 6 and each new one basically will get the oldest in history and update with the newest one, newest one visitedAt will make it the latest
}
