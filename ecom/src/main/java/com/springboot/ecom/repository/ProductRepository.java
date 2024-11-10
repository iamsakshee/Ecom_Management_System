package com.springboot.ecom.repository;

import com.springboot.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p JOIN Category c on p.category.name = c.name")
    Product findByCategoryIdAndName(int categoryId, String name);

    @Query("select p from Product p where p.vendor.id=:id")
    Set<Product> findProductsByVendor(int id);
}
