package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.URIHistory;
import com.sahajdeepsingh.onetabread.service.BookService;
import com.sahajdeepsingh.onetabread.service.URIHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend to access
@RestController
@RequestMapping("/users/{user_id}/books/{book_id}/urihistory")
public class URIHistoryController {
    // So we need a post and get method, maybe a delete method too, also
    // update is for when a url is visited and its already posted, this can change
    // the visited_at so it shows up in front and there is no duplicate
    // post is when a url is newly visited, only if it followed the pattern
    // get is to get the latest 5 url that goes to same book_id
    // delete is for probably deleting a url if not within latest 5 so get is easier
    // delete can amortize the cost of get

    private final URIHistoryService uriHistoryService;
    private final BookService bookService;

    public URIHistoryController(URIHistoryService uriHistoryService, BookService bookService) {
        this.uriHistoryService = uriHistoryService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Void> createURIHistory(@PathVariable Long user_id,
                                                 @PathVariable Long book_id,
                                                 @RequestBody URIHistory uriHistory,
                                                 UriComponentsBuilder ucb) {
        // Sets the current time if it was not provided
        if (uriHistory.getVisitedAt() == null) {
            uriHistory.setVisitedAt(LocalDateTime.now());
        }

        // Get the book and check if it exists
        Book book = bookService.findByIdAndUserId(book_id, user_id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        // Add the book to uri
        uriHistory.setBook(book);

        URIHistory newURIHistory = uriHistoryService.save(uriHistory);
        if (newURIHistory != null) {   // null.anything gives an error
            URI locationOfNewURIHistory = ucb
                    .path("/users/{user_id}/books/{book_id}/urihistory/{id}")
                    .buildAndExpand(user_id, book_id, newURIHistory.getId())
                    .toUri();
            return ResponseEntity.created(locationOfNewURIHistory).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<URIHistory>> getURIHistoryByBookId(@PathVariable String user_id,
                                                                  @PathVariable Long book_id) {
        List<URIHistory> uriHistory = uriHistoryService.getURIHistoryByBookId(book_id);
        if (uriHistory != null && !uriHistory.isEmpty()) {
            return ResponseEntity.ok().body(uriHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<URIHistory> updateURIHistory(@PathVariable Long user_id,
                                                       @PathVariable Long book_id,
                                                       @RequestBody URIHistory uriHistory) {
        // Check if it actually exists
        if (uriHistory == null || uriHistory.getUri() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Verify the book exists
        Book book = bookService.findByIdAndUserId(book_id, user_id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        URIHistory savedURIHistory = uriHistoryService.updateURIHistory(book_id, uriHistory);
        if (savedURIHistory != null) {
            return ResponseEntity.ok(savedURIHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
