package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.ecom.enums.ShipmentStatus;
import com.springboot.ecom.model.Shipment;

@Component
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

	List<Shipment> findByStatus(ShipmentStatus status);

}