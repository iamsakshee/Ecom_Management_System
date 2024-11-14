package com.springboot.ecom.service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.CategoryRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;

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

    public Set<Product> findProductsByVendor(int id) throws ResourceNotFoundException {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));

        Set<Product> products = productRepository.findProductsByVendor(id);

        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for vendor with id: " + id);
        }
        return products;
    }


    public Product updateProduct(Product existingProduct) {
        return productRepository.save(existingProduct);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Set<Product> getProductsByCategoryId(int categoryId) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category invalid "));

        Set<Product> products = productRepository.getAllProductsByCategoryId(categoryId);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this category ");
        }
        return products;
    }

    public Set<Product> getAllProductsByVendor(int vendorId) throws ResourceNotFoundException {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + vendorId));

        Set<Product> products = productRepository.findProductsByVendor(vendorId);

        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for vendor with id: " + vendorId);
        }
        return products;

    }


}
