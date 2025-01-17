package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // POST method
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // GET methods
    public Book findByIdAndUser(Long id, Long user_id) {
        return bookRepository.findByIdAndUserId(id, user_id).orElse(null);
    }   // only this way so users can only access their own books

    public List<Book> getBooksByUserId(Long user_id) {
        return bookRepository.findAllByUserId(user_id);
    }

    // DELETE method
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // PUT methods
    @Transactional
    public Book updateBook(Long user_id, Book book) {
        Book bookToUpdate = bookRepository.findByIdAndUserId(user_id, book.getId()).orElse(null);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setPattern(book.getPattern());
            return bookRepository.save(bookToUpdate);
        }
        return null;
    }
}
