package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Shipment;
import com.springboot.ecom.repository.ShipmentRepository;

	@Service
	public class ShipmentService {

		@Autowired
		private ShipmentRepository shipmentRepository;
		
		public Shipment insert(Shipment shipment) {
			return shipmentRepository.save(shipment);
			
		}

		public List<Shipment> getAllShipment() {
			 
			return shipmentRepository.findAll();
		}

		public void delete(int id) {
			shipmentRepository.deleteById(id);
			
		}
		
		public Shipment validate(int id) throws ResourceNotFoundException {
			Optional<Shipment> optional = shipmentRepository.findById(id);
			if(optional.isEmpty()) {
				throw new ResourceNotFoundException("Shipment Id Invalid");
		}
			return optional.get();
			
		}
}

