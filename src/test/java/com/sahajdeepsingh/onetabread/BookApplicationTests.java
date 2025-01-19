package com.sahajdeepsingh.onetabread;

import com.sahajdeepsingh.onetabread.model.Book;
import com.sahajdeepsingh.onetabread.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = OneTabReadApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateBook() {
        Book book = new Book();
        book.setTitle("shouldCreateBook");

        User user = new User();
        user.setUsername("for_book_testing");
        user.setPassword("for_book_testing");

        ResponseEntity<User> responseEntityUser = restTemplate.getForEntity("/users/findByUsernameAndPassword?requestedUsername={username}&requestedPassword={password}",
                User.class, user.getUsername(), user.getPassword());

        Long userId = responseEntityUser.getBody().getId();

        System.out.println(responseEntityUser.getBody().getId());

        ResponseEntity<Book> responseEntityBook = restTemplate.postForEntity("/users/{user_id}/books", book, Book.class, userId);

        assertThat(responseEntityBook.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI uriLocation = responseEntityBook.getHeaders().getLocation();
        assertThat(uriLocation).isNotNull();
        assertThat(uriLocation.getPath()).startsWith("/users/" + userId + "/books");
    }
/*
    @Test
    void shouldGetBooksByUserId() {
        User user = new User();
        user.setUsername("for_book_testing");
        user.setPassword("for_book_testing");

        ResponseEntity<User> responseEntityUser = restTemplate.getForEntity("/users/findByUsernameAndPassword?requestedUsername={username}&requestedPassword={password}",
                User.class, user.getUsername(), user.getPassword());

        Long userId = responseEntityUser.getBody().getId();

        Book book1 = new Book();
        book1.setTitle("shouldGetBooksByUserId1");
        Book book2 = new Book();
        book2.setTitle("shouldGetBooksByUserId2");

        restTemplate.postForEntity("/users/{user_id}/books", book1, Book.class, userId);
        restTemplate.postForEntity("/users/{user_id}/books", book2, Book.class, userId);

        ResponseEntity<List<Book>> responseEntities = restTemplate.exchange("/users/{user_id}/books", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Book>>() {}, userId);

        assertThat(responseEntities.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntities.getBody()).containsExactlyInAnyOrder(book1, book2);
    }*/
}
