package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.User;
import com.sahajdeepsingh.onetabread.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // POST method
    public User save(User user) {
        return userRepository.save(user);
    }

    // GET methods
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // DELETE method
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // PUT methods
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }
}
