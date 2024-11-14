package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User insert(User user) {
		return userRepository.save(user);
		
	}

	public List<User> getAllUser() {
		 
		return userRepository.findAll();
	}

	public void delete(int id) {
		userRepository.deleteById(id);
		
	}
	
	public User validate(int id) throws ResourceNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if(optional.isEmpty())
			throw new ResourceNotFoundException("User Id Invalid");
		
		User user = optional.get();
		return user; 
		
	}

}