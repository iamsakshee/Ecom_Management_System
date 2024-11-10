package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Customer insertCustomer(Customer customer) {
		
		return customerRepository.save(customer);
	}

	public Customer validate(int id) throws ResourceNotFoundException {
		
		Optional<Customer> optional = customerRepository.findById(id);
		if(optional.isEmpty())
			throw new ResourceNotFoundException("Customer id is invalid");
		
		return optional.get();
		
	}

	public void delete(int id) {
		customerRepository.deleteById(id);
		
	}

	
}
