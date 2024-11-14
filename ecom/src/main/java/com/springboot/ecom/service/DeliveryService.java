package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Delivery;
import com.springboot.ecom.repository.DeliveryRepository;

	@Service
	public class DeliveryService {

		@Autowired
		private DeliveryRepository deliveryRepository;
		
		public Delivery insert(Delivery delivery) {
			return deliveryRepository.save(delivery);
			
		}

		public List<Delivery> getAllDelivery() {
			 
			return deliveryRepository.findAll();
		}

		public void delete(int id) {
			deliveryRepository.deleteById(id);
			
		}
		
		public Delivery validate(int id) throws ResourceNotFoundException {
			Optional<Delivery> optional = deliveryRepository.findById(id);
			if(optional.isEmpty()) {
				throw new ResourceNotFoundException("Delivery Id Invalid");
		}
			return optional.get();
			
		}
}

