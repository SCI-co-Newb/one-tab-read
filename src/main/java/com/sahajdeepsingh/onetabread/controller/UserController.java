package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.model.User;
import com.sahajdeepsingh.onetabread.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // This method finds the user by their id,
    // returns an ok response if found and user json,
    // else returns a not found response
    @GetMapping("/{requestedId}")
    public ResponseEntity<User> findById(@PathVariable Long requestedId) {
        User user = userService.findById(requestedId);

        if (user != null) {
            // 200 meaning OK user is found and redact password
            user.setPassword("*redacted*");
            return ResponseEntity.ok().body(user);
        } else {
            // 404 meaning not founds
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByUsernameAndPassword")
    public ResponseEntity<User> findByUsernameAndPassword(@RequestParam String requestedUsername,
                                               @RequestParam String requestedPassword) {
        User user = userService.findByUsernameAndPassword(requestedUsername, requestedPassword);

        if (user != null) {
            // 200 meaning OK user is found
            return ResponseEntity.ok().body(user);
        } else {
            // 404 meaning not founds
            return ResponseEntity.notFound().build();
        }
    }

    // probably make get methods to findbyusername and findbyusernameandpassword for different scenerios

    // This method saves user into the repository and creates a link so id is linked to the url,
    // making the above function properly
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucb) {
        User savedUser = userService.save(user);
        URI locationOfNewUser = ucb
                .path("/users/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    // update user in database based on changes to user in code
    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        User savedUser = userService.updateUser(user);

        if (savedUser != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam String requestedUsername,
                                           @RequestParam String requestedPassword) {
        User user = userService.findByUsernameAndPassword(requestedUsername, requestedPassword);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }
}
