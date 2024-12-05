package com.springboot.ecom.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
	
	@Entity
	@Table(name = "delivery")
	public class Delivery {

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;
	
	    @Column(nullable = false)
	    private String deliveredToPerson;
	    
	    @Column(nullable = false)
	    private String deliveredByPerson;

	    @Column(nullable = false)
	    private LocalDateTime deliveryDate;

	    @Column(nullable = false)
	    private String proofId;
	
	    @OneToOne
	    private Shipment shipment;
	    
	    @ManyToOne
	    private Warehouse warehouse;

		public Warehouse getWarehouse() {
			return warehouse;
		}

		public void setWarehouse(Warehouse warehouse) {
			this.warehouse = warehouse;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDeliveredToPerson() {
			return deliveredToPerson;
		}

		public void setDeliveredToPerson(String deliveredToPerson) {
			this.deliveredToPerson = deliveredToPerson;
		}

		public String getDeliveredByPerson() {
			return deliveredByPerson;
		}

		public void setDeliveredByPerson(String deliveredByPerson) {
			this.deliveredByPerson = deliveredByPerson;
		}

		public LocalDateTime getDeliveryDate() {
			return deliveryDate;
		}

		public void setDeliveryDate(LocalDateTime deliveryDate) {
			this.deliveryDate = deliveryDate;
		}

		public String getProofId() {
			return proofId;
		}

		public void setProofId(String proofId) {
			this.proofId = proofId;
		}

		public Shipment getShipment() {
			return shipment;
		}

		public void setShipment(Shipment shipment) {
			this.shipment = shipment;
		}

		@Override
		public String toString() {
			return "Delivery [id=" + id + ", deliveredToPerson=" + deliveredToPerson + ", deliveredByPerson="
					+ deliveredByPerson + ", deliveryDate=" + deliveryDate + ", proofId=" + proofId
					+ ", shipment=" + shipment + ", warehouse=" + warehouse
					+ "]";
		}

		
	        
}
