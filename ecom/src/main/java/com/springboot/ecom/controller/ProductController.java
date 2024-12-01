package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.*;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/product/add/{userId}/{categoryId}")
    public ResponseEntity<?> addProduct(@PathVariable int userId,
                                        @PathVariable int categoryId,
                                        @RequestBody Product product) throws ResourceNotFoundException, InvalidUsernameException {
        User user = userService.findByUserId(userId);
        Category category = categoryService.getCategoryById(categoryId);

        product.setCategory(category);
        product.setUser(user);
        Product savedProduct = productService.addProductWithVendorAndCategory(product, user, categoryId);
        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping("/api/product/image/upload/{pid}")
    public ProductImage uploadImage(@PathVariable int pid, @RequestParam MultipartFile file)
            throws IOException, ResourceNotFoundException {

        return productService.uploadImage(pid, file);
    }

    @GetMapping("/products/all/{userId}")
    public ResponseEntity<?> getAllProductsByVendorId(@PathVariable int userId) throws ResourceNotFoundException {
        Set<Product> products = productService.getAllProductsByUser(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable int categoryId) throws ResourceNotFoundException {
        Set<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/getProduct")
    public ResponseEntity<?> getProductsById() throws ResourceNotFoundException {
        //Product product = productService.getProductById(id);
        List<Product> pList = productService.getAllProducts();
        List<ProductImage> imageList = productService.getAllProductImages();

        List<ProductResponseDto> listDto = new ArrayList<>();
        for (Product p : pList) {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setPrice(p.getPrice());

            List<ProductImage> iList =
                    imageList.stream()
                            .filter(i -> i.getProduct().getId() == p.getId())
                            .toList();
            dto.setProductImageList(iList);
            listDto.add(dto);
        }
        return ResponseEntity.ok(listDto);
    }

    @GetMapping("/products-with-images/{vendorId}")
    public ResponseEntity<?> getAllProductsAlongWithImagesForVendor(@PathVariable int vendorId) throws ResourceNotFoundException {
        // Fetch all products for the vendor
        Set<Product> products = productService.getAllProductsByUser(vendorId);

        // Fetch all product images
        List<ProductImage> allImages = productService.getAllProductImages();

        // Prepare response DTOs
        List<ProductResponseDto> responseDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());

            // Filter and map images for this product
            List<ProductImage> productImages = allImages.stream()
                    .filter(image -> image.getProduct().getId() == product.getId())
                    .toList();
            dto.setProductImageList(productImages);

            responseDtoList.add(dto);
        }

        return ResponseEntity.ok(responseDtoList);
    }


    @PutMapping("/product/update/status/{productId}")
    public ResponseEntity<?> updateFeaturedProduct(@PathVariable int productId) throws ResourceNotFoundException {
        Product existingProduct = productService.getProductById(productId);

        productService.updateFeaturedStatus(existingProduct);
        return ResponseEntity.ok(existingProduct);
    }


    @PutMapping("/product/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,
                                           @RequestBody Product updatedProduct) throws ResourceNotFoundException {
        Product existingProduct = productService.getProductById(productId);

        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setName(updatedProduct.getDescription());
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