package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.dto.CustomerShippingDetailsDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.ShippingAddressRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);

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
		return customerRepository.save(customer);

	}

	public Customer insertCustomer(Customer existingCustomer) {
		return customerRepository.save(existingCustomer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public CustomerShippingDetailsDto getCustomerAndShippingDetailsByUsername(String username) {
		List<Object[]> customerDataList = customerRepository.findCustomerDetailsByUsername(username);

		if (!customerDataList.isEmpty()) {
			Object[] customerData = customerDataList.get(0);

			int customerId = (int) customerData[0];
			String customerName = (String) customerData[1];
			String customerEmail = (String) customerData[2];
			String phoneNumber = (String) customerData[3];

			ShippingAddress shippingAddress = shippingAddressRepository.findShippingAddressByCustomerId(customerId);

			CustomerShippingDetailsDto dto = new CustomerShippingDetailsDto();
			dto.setCustomerId(customerId);
			dto.setCustomerName(customerName);
			dto.setCustomerEmail(customerEmail);
			dto.setPhoneNumber(phoneNumber);
			dto.setShippingAddress(shippingAddress);

			return dto;
		}

		return null;
	}

	

}
