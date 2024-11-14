package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.ShippingAddressRepository;

@Service
public class ShippingAddressService {

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	public void validate(int cid) throws ResourceNotFoundException {

		Optional<ShippingAddress> optional = shippingAddressRepository.findById(cid);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("customer id is invalid");

	}

	public ShippingAddress insertShippingAddress(ShippingAddress shippingAddress) {

		return shippingAddressRepository.save(shippingAddress);
	}

	public List<ShippingAddress> getAddressByCity(String city) {

		return shippingAddressRepository.getAddressByCity(city);

	}

	public void deleteAddressbyCustomerId(int cid) {

		shippingAddressRepository.deleteAddressbyCustomerId(cid);

	}

	public ShippingAddress findById(int id) throws ResourceNotFoundException {
		Optional<ShippingAddress> shippingAddress = shippingAddressRepository.findById(id);
		if (shippingAddress.isPresent()) {
			return shippingAddress.get();
		} else {
			throw new ResourceNotFoundException("Shipping address not found for id: " + id);
		}
	}

	public ShippingAddress findByCustomerId(int customerId) {
		
		return shippingAddressRepository.findByCustomerId(customerId);
	}

	

}
