package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/{book_id}")
    public ResponseEntity<Book> getBookByIdAndUserId(@PathVariable Long user_id, @PathVariable Long book_id) {
        Book book = bookService.findByIdAndUserId(book_id, user_id);

        if(book != null) {
            return ResponseEntity.ok().body(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Long user_id) {
        List<Book> books = bookService.getBooksByUserId(user_id);

        if(books != null) {
            return ResponseEntity.ok().body(books);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Void> updateBook(@PathVariable Long user_id, @RequestBody Book book) {
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        Book savedBook = bookService.save(user_id, book);

        if(savedBook != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long user_id, @PathVariable Long book_id) {
        Book getBook = bookService.findByIdAndUserId(book_id, user_id);

        if(getBook == null) {
            return ResponseEntity.notFound().build();
        }

        bookService.deleteById(getBook.getId());
        return ResponseEntity.ok().build();
    }
}
