package com.springboot.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public User signUp(User user) throws InvalidUsernameException {
		Optional<User> optional = userRepository.findByUsername(user.getUsername());
		if (optional.isPresent()) {
			throw new InvalidUsernameException("Username already in use");
		}

		// encrypt the password
		String encryptedPass = passEncoder.encode(user.getPassword());
		user.setPassword(encryptedPass);

		return userRepository.save(user);
	}

	public User validate(int id) throws ResourceNotFoundException {

		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("User id is invalid");
		
		
		return optional.get();

	}

	public User findByUsername(String username) {
		// i am sure that username is valid as Spring has already checked it
		return userRepository.findByUsername(username).get();
	}

	public User updateUserStatus(int id, boolean status) throws ResourceNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("UserId Invalid");

		User user = optional.get();
		user.setEnabled(status);
		return userRepository.save(user);
	}

}
