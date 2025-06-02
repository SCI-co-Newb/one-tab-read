package com.sahajdeepsingh.onetabread.service;

import com.sahajdeepsingh.onetabread.model.User;
import com.sahajdeepsingh.onetabread.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .filter(user -> bCryptPasswordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(null);
    }


    // POST method
    public User save(User user) {
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Username already exists");
        }
    }

    // GET methods
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return authenticate(username, password);
    }

    // DELETE method
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // PUT methods
    public User updateUser(User user) {
        User existingUser = authenticate(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // probably another 1-2 put methods to just change the password or username
}
