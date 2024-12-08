package com.springboot.ecom.dto;

import org.springframework.stereotype.Component;

@Component
public class ManagerDTO {

    private String name;
    private String contact;
    private String username;  // The user that will be associated with the manager
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private int warehouseId; // The warehouse that will be associated with the manager

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
