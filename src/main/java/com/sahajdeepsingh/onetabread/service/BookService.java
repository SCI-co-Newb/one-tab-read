package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // POST method
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // GET methods
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findByTitleAndUser(String title, Long user_id) {
        return bookRepository.findByTitleAndUserId(title, user_id);
    }

    // DELETE method
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // PUT methods
    public Book updateBook(Book book) {
        Book bookToUpdate = bookRepository.findById(book.getId()).orElse(null);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setHistory(book.getHistory());
            bookToUpdate.setUser(book.getUser());
            bookToUpdate.setPattern(book.getPattern());
            return bookRepository.save(bookToUpdate);
        }
        return null;
    }
}
