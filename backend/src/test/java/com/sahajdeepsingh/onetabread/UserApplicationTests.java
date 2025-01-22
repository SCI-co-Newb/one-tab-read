package com.sahajdeepsingh.onetabread;

import com.sahajdeepsingh.onetabread.model.User;
import org.junit.jupiter.api.AfterEach;
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
class UserApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    User newUser;

    @AfterEach
    void tearDown() {
        restTemplate.delete("/users/deleteUser?requestedUsername={username}&requestedPassword={password}",
                newUser.getUsername(), newUser.getPassword());
    }

    @Test
    void shouldCreateUser() {
        newUser = new User();
        newUser.setUsername("adminCreate");
        newUser.setPassword("adminCreate");
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/users", newUser, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location).isNotNull();
        assertThat(location.getPath()).startsWith("/users/");
    }

    @Test
    void shouldFindUserById() {
        newUser = new User();
        newUser.setUsername("adminFindById");
        newUser.setPassword("adminFindById");
        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users", newUser, Void.class);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long newUserId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/{id}", User.class, newUserId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldFindUserByUsernameAndPassword() {
        newUser = new User();
        newUser.setUsername("adminFindByUsername");
        newUser.setPassword("adminFindByUsername");
        restTemplate.postForEntity("/users", newUser, Void.class);

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/findByUsernameAndPassword?requestedUsername={username}&requestedPassword={password}",
                User.class, newUser.getUsername(), newUser.getPassword());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getUsername()).isEqualTo("adminFindByUsername");
        assertThat(responseEntity.getBody().getPassword()).isEqualTo("adminFindByUsername");
    }

    @Test
    void shouldUpdateUser() {
        newUser = new User();
        newUser.setUsername("adminUpdate");
        newUser.setPassword("adminUpdate");
        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users", newUser, Void.class);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long newUserId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
        newUser.setId(newUserId);   // since the object doesn't know the generated id

        newUser.setUsername("adminUpdateUpdate");
        newUser.setPassword("adminUpdateUpdate");
        restTemplate.put("/users/updateUser", newUser);

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/findByUsernameAndPassword?requestedUsername={username}&requestedPassword={password}",
                User.class, newUser.getUsername(), newUser.getPassword());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getUsername()).isEqualTo("adminUpdateUpdate");
        assertThat(responseEntity.getBody().getPassword()).isEqualTo("adminUpdateUpdate");
    }

    @Test
    void shouldDeleteUser() {
        newUser = new User();
        newUser.setUsername("adminDelete");
        newUser.setPassword("adminDelete");
        ResponseEntity<Void> postResponse = restTemplate.postForEntity("/users", newUser, Void.class);

        String location = Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
        Long newUserId = (Long) Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

        restTemplate.delete("/users/deleteUser?requestedUsername={username}&requestedPassword={password}",
                newUser.getUsername(), newUser.getPassword());

        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/{id}", User.class, newUserId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}
