package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Manager;
import com.springboot.ecom.model.Warehouse;
import com.springboot.ecom.repository.ManagerRepository;
import com.springboot.ecom.repository.WarehouseRepository;

import jakarta.transaction.Transactional;

	@Service
	public class ManagerService {

		@Autowired
		private ManagerRepository managerRepository;
		
		@Autowired
		WarehouseRepository warehouseRepository;
	
		
		public Manager insert(Manager manager) {
			return managerRepository.save(manager);
			
		}

		public List<Manager> getAllManager() {
			 
			return managerRepository.findAll();
		}

		public void delete(int id) {
			managerRepository.deleteById(id);
			
		}
		
		public Manager validate(int id) throws ResourceNotFoundException {
			Optional<Manager> optional = managerRepository.findById(id);
			if(optional.isEmpty()) {
				throw new ResourceNotFoundException("Manager Id Invalid");
		}
			return optional.get();
			
		}

		public Optional<Manager> getManagerByUsername(String username) {
			return managerRepository.findByUserUsername(username);
		}

//	    public Manager updateManager(String username, Manager manager) {
//	    	getManagerByUsername(username);
//	        return managerRepository.save(manager);
//	    }      
//	    
	  
		@Transactional
	    public Manager updateManager(String username, Manager manager) {
	        // Step 1: Get existing manager
	        Manager existingManager = managerRepository.findByUserUsername(username)
	                                   .orElseThrow(() -> new RuntimeException("Manager not found"));

	        // Step 2: Update the required fields
	        existingManager.setName(manager.getName()); // Update name
	        existingManager.setContact(manager.getContact()); // Update contact
	        
	        // Update warehouse if warehouse details are provided
	        if (manager.getWarehouse() != null && manager.getWarehouse().getId() != 1) {
	            Warehouse warehouse = warehouseRepository.findById(manager.getWarehouse().getId())
	                                  .orElseThrow(() -> new RuntimeException("Warehouse not found"));
	            
	            if (manager.getWarehouse().getCity() != null) {
	                warehouse.setCity(manager.getWarehouse().getCity()); 
	                warehouseRepository.save(warehouse);
	            }
	            existingManager.setWarehouse(warehouse);
	        }

	        // Step 3: Save and return updated manager
	        return managerRepository.save(existingManager);
	    }
}

