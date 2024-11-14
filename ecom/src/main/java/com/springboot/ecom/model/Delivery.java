package com.springboot.ecom.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
	
	@Entity
	public class Delivery {

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;
	    
	    @Column(nullable = false, unique = true)
	    private String deliveryPerson;

	    @Column(nullable = false)
	    private LocalDateTime deliveryDate;

	    @Column(nullable = false)
	    private String proofid;
	    
	    @Column(nullable = false)
	    private String specialInstruction;
	    
	    private boolean enabled=true; 

		
	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDeliveryPerson() {
			return deliveryPerson;
		}

		public void setDeliveryPerson(String deliveryPerson) {
			this.deliveryPerson = deliveryPerson;
		}

		public LocalDateTime getDeliveryDate() {
			return deliveryDate;
		}

		public void setDeliveryDate(LocalDateTime deliveryDate) {
			this.deliveryDate = deliveryDate;
		}

		public String getProofid() {
			return proofid;
		}

		public void setProofid(String proofid) {
			this.proofid = proofid;
		}

		public String getSpecialInstruction() {
			return specialInstruction;
		}

		public void setSpecialInstruction(String specialInstruction) {
			this.specialInstruction = specialInstruction;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	
}
