package com.springboot.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.ShippingAddressRepository;

@Service
public class ShippingAddressService {
	
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	public List<ShippingAddress> getAddressByCity(String city) {

		return shippingAddressRepository.getAddressByCity(city);

	}

}
