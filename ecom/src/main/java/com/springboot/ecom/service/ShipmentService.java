package com.springboot.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.ecom.enums.ShipmentStatus;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Shipment;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.ShipmentRepository;
import com.springboot.ecom.repository.ShippingAddressRepository;

@Service
public class ShipmentService {

	@Autowired
	private ShipmentRepository shipmentRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	public List<Shipment> getShipmentsByStatus(ShipmentStatus status) {
		return shipmentRepository.findByStatus(status);
	}


	public void delete(int id) {
		shipmentRepository.deleteById(id);

	}

	public Shipment validate(int id) throws ResourceNotFoundException {
		Optional<Shipment> optional = shipmentRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Shipment Id Invalid");
		}
		return optional.get();

	}

	public Shipment getShipment(int id) {
		return shipmentRepository.findById(id).orElse(null);
	}

	public ShippingAddress insertAddress(ShippingAddress shippingAddress) {

		shippingAddress.getAddressLine1();
		shippingAddress.getCity();
		shippingAddress.getCountry();
		shippingAddress.getState();
		shippingAddress.getZipCode();
		return shippingAddressRepository.save(shippingAddress);
	}

	public Shipment insertShipment(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}

	public Shipment updateShipment(Shipment shipment) {
		return shipmentRepository.save(shipment); // Save the updated shipment to the database
	}

	public Page<Shipment> getAllShipment(Pageable pageable) {
		return shipmentRepository.findAll(pageable);
	}


	public List<Shipment> getAllShipment() {
		return shipmentRepository.findAll();
	}


}
