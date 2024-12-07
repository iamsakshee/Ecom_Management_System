package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	
	
	 @Query("select pi from ProductImage pi where pi.product.id=:productId")
	    List<ProductImage> findProductImagesByProductId(int productId);

}
