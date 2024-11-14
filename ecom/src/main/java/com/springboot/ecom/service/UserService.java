package com.springboot.ecom.service;

import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (Role.VENDOR.equals(user.getRole())) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        String encryptedPass = passEncoder.encode(user.getPassword());
        user.setPassword(encryptedPass);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }


    public User updateUserStatus(int id, boolean status) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new ResourceNotFoundException("UserId is Invalid");

        User user = optional.get();
        user.setEnabled(status);
        return userRepository.save(user);
    }

    public User findByUserId(int userId) throws ResourceNotFoundException {

        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty())
            throw new ResourceNotFoundException("User id is invalid");

        return optional.get();
    }
}
