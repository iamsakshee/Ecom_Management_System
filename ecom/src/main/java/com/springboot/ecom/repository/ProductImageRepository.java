package com.springboot.ecom.repository;

import com.springboot.ecom.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    @Query("select pi from ProductImage pi where pi.product.id=:productId")
    List<ProductImage> findProductImagesByProductId(int productId);
}
