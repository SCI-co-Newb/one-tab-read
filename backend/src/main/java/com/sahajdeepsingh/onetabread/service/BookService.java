package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.User;
import com.sahajdeepsingh.onetabread.repository.BookRepository;
import com.sahajdeepsingh.onetabread.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // POST method
    @Transactional
    public Book save(Long user_id, Book book) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user != null) {
            book.setUser(user);
            return bookRepository.save(book);
        }
        return null;
    }

    // GET methods
    public Book findByIdAndUserId(Long id, Long user_id) {
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
        Book bookToUpdate = bookRepository.findByIdAndUserId(book.getId(), user_id).orElse(null);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setPattern(book.getPattern());
            return bookRepository.save(bookToUpdate);
        }
        return null;
    }
}
