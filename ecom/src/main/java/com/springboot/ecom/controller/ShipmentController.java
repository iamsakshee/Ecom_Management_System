package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.enums.ShipmentStatus;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Shipment;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.service.ShipmentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ShipmentController {

	@Autowired
	private ResponseMessageDto responseMessageDto;

	@Autowired
	private ShipmentService shipmentService;

	@PostMapping("/shipment/add")
	public Shipment addUser(@RequestBody Shipment shipment) {
		ShippingAddress shippingAddress = shipment.getShippingAddress();
		shippingAddress = shipmentService.insertAddress(shippingAddress);

		shipment.setShippingAddress(shippingAddress);

		return shipmentService.insertShipment(shipment);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<Shipment>> getShipmentsByStatus(@PathVariable ShipmentStatus status) {
		List<Shipment> shipments = shipmentService.getShipmentsByStatus(status);
		if (shipments.isEmpty()) {
			return ResponseEntity.noContent().build(); // Return 204 if no shipments found
		}
		return ResponseEntity.ok(shipments);
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

	@PutMapping("/shipment/update-status/{id}")
	public ResponseEntity<?> updateShipmentStatus(@PathVariable int id, @RequestBody ShipmentStatus status) {
		try {
			Shipment shipment = shipmentService.getShipment(id); // Fetch the shipment

			shipment.setStatus(status); // Update the status
			Shipment updatedShipment = shipmentService.updateShipment(shipment); // Save changes

			return ResponseEntity.ok(updatedShipment); // Return the updated shipment
		} catch (Exception e) {
			responseMessageDto.setMsg("Error updating shipment status: " + e.getMessage());
			return ResponseEntity.badRequest().body(responseMessageDto);
		}
	}
	
	@GetMapping("/shipment/all")
	public List<Shipment> getShipments(){
		return shipmentService.getAllShipment();
	}
	

	@GetMapping("/api/shipment/all")
	public Page<Shipment> getShipments(@RequestParam(required = false, defaultValue = "0") String page,
			@RequestParam(required = false, defaultValue = "1000000") String size) throws Exception {
		Pageable pageable = null;

		try {
			pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
		} catch (Exception e) {
			throw e;
		}

		Page<Shipment> list = shipmentService.getAllShipment(pageable);
		return list;
	}

}
