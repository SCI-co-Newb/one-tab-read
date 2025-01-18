package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users/{user_id}/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@PathVariable Long user_id, @RequestBody Book book, UriComponentsBuilder ucb) {
        Book newBook = bookService.save(user_id, book);
        if(newBook != null) {
            URI locationOfNewBook = ucb
                    .path("/users/{user_id}/books/{id}")
                    .buildAndExpand(user_id, newBook.getId())
                    .toUri();
            return ResponseEntity.created(locationOfNewBook).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
