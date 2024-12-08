package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductWishlist;
import com.springboot.ecom.model.Wishlist;

import jakarta.transaction.Transactional;

public interface ProductWishlistRepository extends JpaRepository<ProductWishlist, Integer> {

	boolean existsByWishlistAndProduct(Wishlist wishlist, Product product);

	List<ProductWishlist> findByWishlist(Wishlist wishlist);

	@Modifying
	@Transactional
	@Query("Delete from ProductWishlist pr where pr.product.id = ?1")
	void deleteWishlistProductById(int pid);

}
