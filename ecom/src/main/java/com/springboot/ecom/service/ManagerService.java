package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Manager;
import com.springboot.ecom.repository.ManagerRepository;

	@Service
	public class ManagerService {

		@Autowired
		private ManagerRepository managerRepository;
		
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
}

