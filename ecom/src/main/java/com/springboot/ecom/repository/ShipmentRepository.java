package com.springboot.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.ecom.model.Shipment;

@Component
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

}