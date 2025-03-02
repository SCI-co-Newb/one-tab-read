package com.sahajdeepsingh.onetabread;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = OneTabReadApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    Long userId;

    @BeforeEach
    /*
    * Resets the user being used for this test class by deleting then recreating.
    * This cascade deletes all the books associated with the user, making a clean slate for each test.
    */
    void setUp() {
        User user = new User();
        user.setUsername("for_book_testing");
        user.setPassword("for_book_testing");

        restTemplate.delete("/users/deleteUser?requestedUsername={username}&requestedPassword={password}",
                user.getUsername(), user.getPassword());

        ResponseEntity<User> responseEntityUserDelete = restTemplate.postForEntity("/users/findByUsernameAndPassword",
                user, User.class);

        assertThat(responseEntityUserDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        restTemplate.postForEntity("/users", user, User.class);

        ResponseEntity<User> responseEntityUser = restTemplate.postForEntity("/users/findByUsernameAndPassword",
                user, User.class);

        assertThat(responseEntityUser.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntityUser.getBody()).isNotNull();

        userId = responseEntityUser.getBody().getId();
    }

    @Test
    void shouldCreateBook() {
        Book book = new Book();
        book.setTitle("shouldCreateBook");

        ResponseEntity<Book> responseEntityBook = restTemplate.postForEntity("/users/{user_id}/books", book, Book.class, userId);

        assertThat(responseEntityBook.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI uriLocation = responseEntityBook.getHeaders().getLocation();
        assertThat(uriLocation).isNotNull();
        assertThat(uriLocation.getPath()).startsWith("/users/" + userId + "/books");
    }

    @Test
    void shouldGetBooksByUserId() {
        Book book1 = new Book();
        book1.setTitle("shouldGetBooksByUserId1");
        Book book2 = new Book();
        book2.setTitle("shouldGetBooksByUserId2");

        restTemplate.postForEntity("/users/{user_id}/books", book1, Book.class, userId);
        restTemplate.postForEntity("/users/{user_id}/books", book2, Book.class, userId);

        ResponseEntity<List<Book>> responseEntities = restTemplate.exchange("/users/{user_id}/books", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Book>>() {}, userId);

        assertThat(responseEntities.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntities.getBody())
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder(book1.getTitle(), book2.getTitle());
    }

    @Test
    void shouldFindByIdAndUserId() {
        Book book = new Book();
        book.setTitle("shouldFindByIdAndUserId");

        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users/{user_id}/books", book, Void.class, userId);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long bookId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

        ResponseEntity<Book> responseEntity = restTemplate.getForEntity("/users/{user_id}/books/{id}", Book.class, userId, bookId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(bookId);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void shouldUpdateBook() {
        Book book = new Book();
        book.setTitle("shouldUpdateBook1");

        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users/{user_id}/books", book, Void.class, userId);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long bookId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
        book.setId(bookId); // since book doesn't know the generated id

        book.setTitle("shouldUpdateBook2");

        ResponseEntity<Void> putEntity = restTemplate.exchange(
                "/users/{user_id}/books",
                HttpMethod.PUT,
                new HttpEntity<>(book),
                Void.class,
                userId
        );

        assertThat(putEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Book> responseEntity = restTemplate.getForEntity("/users/{user_id}/books/{id}", Book.class, userId, bookId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(bookId);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void shouldDeleteBook() {
        Book book = new Book();
        book.setTitle("shouldDeleteBook");

        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users/{user_id}/books", book, Void.class, userId);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long bookId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

        restTemplate.delete("/users/{user_id}/books/{id}", userId, bookId);

        ResponseEntity<Book> responseEntity = restTemplate.getForEntity("/users/{user_id}/books/{id}", Book.class, userId, bookId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
