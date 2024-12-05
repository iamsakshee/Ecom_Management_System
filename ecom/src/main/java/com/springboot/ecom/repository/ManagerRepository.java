package com.springboot.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.ecom.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	    Optional<Manager> findByUserUsername(String username);
}
