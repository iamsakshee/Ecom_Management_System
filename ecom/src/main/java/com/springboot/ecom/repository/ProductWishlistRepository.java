package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductWishlist;
import com.springboot.ecom.model.Wishlist;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Integer> {
	
	boolean existsByWishlistAndProduct(Wishlist wishlist, Product product);
	
	  List<ProductWishlist> findByWishlist(Wishlist wishlist);

}
