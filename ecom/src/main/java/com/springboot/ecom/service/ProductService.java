package com.springboot.ecom.service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    public Product addProductWithVendorAndCategory(Product product, Vendor vendor, int categoryId) throws ResourceNotFoundException {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        product.setCategory(category);
        product.setVendor(vendor);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) throws ResourceNotFoundException {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Product id invalid");
        }
        return optional.get();
    }

    public Set<Product> findProductsByVendor(int id) {
        return productRepository.findProductsByVendor(id);
    }

    public Product updateProduct(Product existingProduct) {
        return productRepository.save(existingProduct);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Set<Product> getProductsByCategoryId(int categoryId) {
        Set<Product> products = productRepository.getAllProductsByCategoryId(categoryId);
        return products;
    }

	public Product validate(int id) throws ResourceNotFoundException {
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Customer id is invalid");

		return optional.get();
		
	}


}
