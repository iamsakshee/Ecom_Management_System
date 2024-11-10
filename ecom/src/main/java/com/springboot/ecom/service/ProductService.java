package com.springboot.ecom.service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Set<Product> findProductsByVendor(int id)
    {
        return productRepository.findProductsByVendor(id);
    }
}
