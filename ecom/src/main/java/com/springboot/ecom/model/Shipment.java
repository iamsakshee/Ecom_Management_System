package com.springboot.ecom.model;

import java.time.LocalDateTime;

import com.springboot.ecom.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shipment {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ShipmentStatus getStatus() {
		return status;
	}

	public void setStatus(ShipmentStatus status) {
		this.status = status;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}    

}
