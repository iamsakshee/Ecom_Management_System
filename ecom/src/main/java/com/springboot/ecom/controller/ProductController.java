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

import java.util.ArrayList;
import java.util.List;
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
                                                  @RequestBody Product product,
                                                  ResponseMessageDto dto) {
        Vendor vendor = null;
        try {
            vendor = vendorService.getVendorById(vendorId);
        } catch (ResourceNotFoundException e) {
            dto.setMsg("Vendor not found with id: " + vendorId);
            return ResponseEntity.badRequest().body(dto);
        }
        Category category = null;
        try {
            category = categoryService.getCategoryById(categoryId);
        } catch (ResourceNotFoundException e) {
            dto.setMsg("Category not found with id: " + categoryId);
            return ResponseEntity.badRequest().body(dto);
        }
        product.setCategory(category);
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);
        product.setVendor(vendor);
        try {
            product = productService.addProductWithVendorAndCategory(product, vendor, categoryId);
        } catch (ResourceNotFoundException e) {
            dto.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = productService.getAllProducts();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable int categoryId, ResponseMessageDto dto)
    {
        try {
            Set<Product> list = productService.getProductsByCategoryId(categoryId);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/getProduct/{id}")
    public ResponseEntity<Product> getProductsById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,
                                           @RequestBody Product updatedProduct,
                                           ResponseMessageDto dto) {
        Product existingProduct = null;
        try {
            existingProduct = productService.getProductById(productId);
        } catch (ResourceNotFoundException e) {
            dto.setMsg("Product not found with id: " + productId);
            return ResponseEntity.badRequest().body(dto);
        }

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
    public ResponseEntity<?> deleteVendor(@PathVariable int id, ResponseMessageDto dto)
    {
        try {
            productService.getProductById(id);
            productService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            dto.setMsg(dto.getMsg());
            return ResponseEntity.badRequest().body(dto);
        }
        dto.setMsg("Product deleted");
        return ResponseEntity.ok(dto);
    }
}
