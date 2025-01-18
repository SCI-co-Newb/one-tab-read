package com.sahajdeepsingh.onetabread;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = OneTabReadApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateBook() {
        Book book = new Book();
        book.setTitle("Book Title");

        User user = new User();
        user.setUsername("for_book_testing");
        user.setPassword("for_book_testing");

        ResponseEntity<User> responseEntityUser = restTemplate.getForEntity("/users/findByUsernameAndPassword?requestedUsername={username}&requestedPassword={password}",
                User.class, user.getUsername(), user.getPassword());

        Long userId = responseEntityUser.getBody().getId();

        ResponseEntity<Book> responseEntityBook = restTemplate.postForEntity("/users/{user_id}/books", book, Book.class, userId);

        assertThat(responseEntityBook.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI uriLocation = responseEntityBook.getHeaders().getLocation();
        assertThat(uriLocation).isNotNull();
        assertThat(uriLocation.getPath()).startsWith("/users/" + userId + "/books");
    }
}
