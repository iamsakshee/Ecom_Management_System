package com.springboot.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	public User signUp(User user) throws InvalidUsernameException {
		Optional<User> optional = userRepository.findByUsername(user.getUsername());
		if(optional.isPresent()) {
			throw new InvalidUsernameException("Username already in use");
		}

		String encryptedPass = passEncoder.encode(user.getPassword());
		user.setPassword(encryptedPass);
		
		user.setRole(Role.MANAGER);
		
		return userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}
	
	public User findByUserId(int userId) throws ResourceNotFoundException {
		
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("User id is invalid");
		
		return optional.get();
	}
	
	public User findById(int id) {
        return userRepository.findById(id).orElse(null); 
    }
}