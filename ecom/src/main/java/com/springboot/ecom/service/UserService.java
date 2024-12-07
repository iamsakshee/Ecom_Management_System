package com.springboot.ecom.service;

import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    public User signup(User user) throws InvalidUsernameException {
        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            throw new InvalidUsernameException("Username already in use");
        }
        user.setRole(Role.VENDOR);
        if (Role.VENDOR.equals(user.getRole())) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        String encryptedPass = passEncoder.encode(user.getPassword());
        user.setPassword(encryptedPass);
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }


    public User findByUserId(int userId) throws ResourceNotFoundException, InvalidUsernameException {
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("User id is invalid");
        }
        User user = optional.get();
        if (user.getId() <= 0) {
            throw new InvalidUsernameException("Invalid username");
        }

        return user;
    }


    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
