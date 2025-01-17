package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{user_id}/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@PathVariable Long user_id, @RequestBody Book book) {
        Book newBook = bookService.save(user_id, book);
        if(newBook == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }
}
