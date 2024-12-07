package com.springboot.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductWishlist;
import com.springboot.ecom.model.Wishlist;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.ProductWishlistRepository;
import com.springboot.ecom.repository.WishlistRepository;

@Service
public class WishlistService {
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private ProductWishlistRepository productWishlistRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	

	
	public void addProductToWishlist(int customerId, int productId) {
        // Fetch customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if wishlist exists for the customer
        Wishlist wishlist = wishlistRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setCustomer(customer);
                    return wishlistRepository.save(newWishlist);
                });

        // Check if the product is already in the wishlist
        boolean isProductAlreadyInWishlist = productWishlistRepository.existsByWishlistAndProduct(wishlist, product);

        if (isProductAlreadyInWishlist) {
            throw new RuntimeException("Product is already in the wishlist.");
        }

        // Add product to wishlist
        ProductWishlist productWishlist = new ProductWishlist();
        productWishlist.setWishlist(wishlist);
        productWishlist.setProduct(product);

        productWishlistRepository.save(productWishlist);
    }
	
	
	 public List<Product> getWishlistProductsByCustomer(int customerId) {
	        // Fetch customer
	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

	        // Fetch wishlist for the customer
	        Wishlist wishlist = wishlistRepository.findByCustomer(customer)
	                .orElseThrow(() -> new RuntimeException("Wishlist not found for customer"));

	        // Fetch all ProductWishlist entries for this wishlist and map to Product
	        return productWishlistRepository.findByWishlist(wishlist).stream()
	                .map(ProductWishlist::getProduct)
	                .collect(Collectors.toList());
	    }
	
	

}
