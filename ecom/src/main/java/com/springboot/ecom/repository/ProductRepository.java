package com.springboot.ecom.repository;

import com.springboot.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.vendor.id=:id")
    Set<Product> findProductsByVendor(int id);

    @Query("select p from Product p where p.category.id=:categoryId")
    Set<Product> getAllProductsByCategoryId(int categoryId);

    @Query("SELECT p.id, p.name, p.price, p.stock FROM Product p")
	List<Product> findAllProductFields();
}
