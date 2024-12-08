package com.springboot.ecom.repository;

import com.springboot.ecom.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.vendor.user.username=:username")
    Set<Product> findProductsByVendor(String username);

    @Query("select p from Product p where p.category.id=:categoryId")
    List<Product> getAllProductsByCategoryId(int categoryId);
    
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase
    				(String key1, String key2, Pageable pageable);

    
}