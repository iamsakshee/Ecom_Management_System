package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Shipment;
import com.springboot.ecom.service.ShipmentService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class ShipmentController {
	
	@Autowired
	private ResponseMessageDto responseMessageDto;

	@Autowired
	private ShipmentService shipmentService;
	
	@PostMapping("/shipment/add")
	public Shipment addUser(@RequestBody Shipment shipment) {
		 System.out.println(shipment);
		return shipmentService.insert(shipment);
	}
	
	@GetMapping("/shipment/{id}")
    public ResponseEntity<?> getShipment(@PathVariable int id) {
        Shipment shipment = shipmentService.getShipment(id);
        if (shipment != null) {
            return ResponseEntity.ok(shipment); 
        } else {
            responseMessageDto.setMsg("Shipment Not Found");
            return ResponseEntity.badRequest().body(responseMessageDto); 
        }
    }
	
	@GetMapping("/shipment/all")
	public List<Shipment> getAllShipment() {
		List<Shipment> list = shipmentService.getAllShipment();
		return list;
	}

	
	@DeleteMapping("/shipment/delete/{id}")
	public ResponseEntity<?> deleteShipment(@PathVariable int id, ResponseMessageDto dto) {
		try {
			shipmentService.validate(id);
			shipmentService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("Shipment Deleted");
		return ResponseEntity.ok(dto);
	}
}
	