package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.ShippingAddressRepository;

@Service
public class ShippingAddressService {
	
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	public void validate(int cid) throws ResourceNotFoundException {
		
		Optional<ShippingAddress> optional = shippingAddressRepository.findById(cid);
		if(optional.isEmpty())
			throw new ResourceNotFoundException("customer id is invalid");
		
	}

	
	public ShippingAddress insertShippingAddress(ShippingAddress shippingAddress) {
		
		return shippingAddressRepository.save(shippingAddress);
	}


	public List<ShippingAddress> getAddressByCity(String city) {
		
		return shippingAddressRepository.getAddressByCity(city);
		
	}

}
