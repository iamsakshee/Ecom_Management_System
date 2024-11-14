package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.ecom.model.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {

	
	@Query("select s from ShippingAddress s where s.city = ?1")
	List<ShippingAddress> getAddressByCity(String city);
	

	
	@Modifying
	@Transactional
	@Query("DELETE FROM ShippingAddress sa WHERE sa.customer.id = ?1 ")
	void deleteAddressbyCustomerId(int cid);



	@Query("select sa from ShippingAddress sa where sa.customer.id = ?1")
	ShippingAddress findByCustomerId(int customerId);

}
