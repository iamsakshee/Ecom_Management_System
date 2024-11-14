package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Warehouse;
import com.springboot.ecom.repository.WarehouseRepository;

	@Service
	public class WarehouseService {

		@Autowired
		private WarehouseRepository warehouseRepository;
		
		public Warehouse insert(Warehouse warehouse) {
			return warehouseRepository.save(warehouse);
			
		}

		public List<Warehouse> getAllWarehouse() {
			 
			return warehouseRepository.findAll();
		}

		public void delete(int id) {
			warehouseRepository.deleteById(id);
			
		}
		
		public Warehouse validate(int id) throws ResourceNotFoundException {
			Optional<Warehouse> optional = warehouseRepository.findById(id);
			if(optional.isEmpty()) {
				throw new ResourceNotFoundException("Warehouse Id Invalid");
		}
			return optional.get();
			
		}
}

