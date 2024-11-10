package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {

	
	@Query("select s from ShippingAddress s where s.city = ?1")
	List<ShippingAddress> getAddressByCity(String city);

	

}
