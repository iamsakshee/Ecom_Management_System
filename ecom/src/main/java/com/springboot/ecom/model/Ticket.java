package com.springboot.ecom.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ManyToAny;

import com.springboot.ecom.enums.TicketPriority;
import com.springboot.ecom.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	private int id;

	@Column(length = 1000)
	private String subject;

	@Column(length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	private TicketPriority priority;

	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	private Timestamp creation_date;

	@ManyToOne
	private Customer customer;

	// private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Timestamp getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", subject=" + subject + ", description=" + description + ", priority=" + priority
				+ ", status=" + status + ", creation_date=" + creation_date + ", customer=" + customer + "]";
	}

}
