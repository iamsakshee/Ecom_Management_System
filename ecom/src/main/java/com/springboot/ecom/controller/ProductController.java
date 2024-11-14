package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/product/add/{vendorId}/{categoryId}")
    public ResponseEntity<?> addProductToCategory(@PathVariable int vendorId,
                                                  @PathVariable int categoryId,
                                                  @RequestBody Product product) throws ResourceNotFoundException {
        Vendor vendor = vendorService.getVendorById(vendorId);
        Category category = categoryService.getCategoryById(categoryId);

        product.setCategory(category);
        product.setVendor(vendor);
        Product savedProduct = productService.addProductWithVendorAndCategory(product, vendor, categoryId);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/products/all/{vendorId}")
    public ResponseEntity<?> getAllProductsByVendorId(@PathVariable int vendorId) throws ResourceNotFoundException {
        Set<Product> products = productService.getAllProductsByVendor(vendorId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable int categoryId) throws ResourceNotFoundException {
        Set<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/getProduct/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable int id) throws ResourceNotFoundException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,
                                                 @RequestBody Product updatedProduct) throws ResourceNotFoundException {
        Product existingProduct = productService.getProductById(productId);

        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getPrice() > 0) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getStock() >= 0) {
            existingProduct.setStock(updatedProduct.getStock());
        }

        Product updated = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws ResourceNotFoundException {
        productService.getProductById(id);
        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }
}