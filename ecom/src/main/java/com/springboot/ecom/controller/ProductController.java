package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.enums.FeaturedRequest;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.*;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/product/add/{vendorId}/{categoryId}")
    public ResponseEntity<?> addProduct(@PathVariable int vendorId,
                                        @PathVariable int categoryId,
                                        @RequestBody Product product) throws ResourceNotFoundException, InvalidUsernameException {
        Vendor vendor = vendorService.getVendorById(vendorId);
        Category category = categoryService.getCategoryById(categoryId);

        product.setCategory(category);
        product.setVendor(vendor);
        Product savedProduct = productService.addProductWithVendorAndCategory(product, vendor, category);
        return ResponseEntity.ok(savedProduct);
    }


    @PostMapping("/api/product/image/upload/{pid}")
    public ProductImage uploadImage(@PathVariable int pid, @RequestParam MultipartFile file)
            throws IOException, ResourceNotFoundException {

        return productService.uploadImage(pid, file);
    }


    @GetMapping("/products/vendor/all")
    public ResponseEntity<?> getAllProductsByVendorId() throws ResourceNotFoundException {
        Set<Product> products = productService.getAllProductsByVendor();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable int categoryId) throws ResourceNotFoundException {
        Set<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/product/all")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> pList =  productService.getAllProducts();
        List<ProductImage> imageList= productService.getAllProductImages();

        List<ProductResponseDto> listDto = new ArrayList<>();
        for(Product p : pList) {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setPrice(p.getPrice());

            List<ProductImage> iList =
                    imageList.stream()
                            .filter(i->i.getProduct().getId() == p.getId())
                            .toList();
            System.out.println(iList);
            dto.setImages(iList);
            System.out.println(dto);
            listDto.add(dto);
        }

        return listDto;
    }

    @GetMapping("/product/getProduct/{productId}")
    public ResponseEntity<?> getAllProductById(@PathVariable int productId) throws ResourceNotFoundException {

        Product product = productService.getProductById(productId);
        List<ProductImage> imageList = productService.getAllProductImagesByProductId(productId);

        List<ProductResponseDto> listDto = new ArrayList<>();
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        dto.setPrice(product.getPrice());

        List<ProductImage> iList =
                imageList.stream()
                        .filter(i -> i.getProduct().getId() == product.getId())
                        .toList();
        dto.setImages(imageList);
        listDto.add(dto);
        return ResponseEntity.ok(listDto);
    }

    @GetMapping("/products-with-images/{vendorId}")
    public ResponseEntity<?> getAllProductsAlongWithImagesForVendor(@PathVariable int vendorId) throws ResourceNotFoundException {

        Set<Product> products = productService.getAllProductsByVendor();


        List<ProductImage> allImages = productService.getAllProductImages();

        List<ProductResponseDto> responseDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());

            List<ProductImage> productImages = allImages.stream()
                    .filter(image -> image.getProduct().getId() == product.getId())
                    .toList();
            dto.setImages(productImages);

            responseDtoList.add(dto);
        }

        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/product-images/{productId}")
    public List<ProductImage> getProductImagesByProductId(@PathVariable int productId) throws ResourceNotFoundException {
        return productService.getAllProductImagesByProductId(productId);
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
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getBrand() != null) {
            existingProduct.setBrand(updatedProduct.getBrand());
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