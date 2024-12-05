package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.UserRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public Customer validate(int id) throws ResourceNotFoundException {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Customer id is invalid");
		return optional.get();
	}

	public void delete(int id) {
		customerRepository.deleteById(id);
	}

	public List<Customer> getCustomerByZipcode(Integer zipcode) {
		return customerRepository.getCustomerByZipcode(zipcode);
	}

	public Customer insert(Customer customer) {
		
//		User user = customer.getUser();
//		user.setRole(Role.CUSTOMER);
//		String encPassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encPassword);
//		user = userRepository.save(user); // complete user with role, password and id
//		customer.setUser(user);
		return customerRepository.save(customer);

	}

	public Customer insertCustomer(Customer existingCustomer) {
		return customerRepository.save(existingCustomer);
	}

}
