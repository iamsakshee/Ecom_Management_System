package com.springboot.ecom.service;

import com.springboot.ecom.enums.FeaturedRequest;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductImage;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.CategoryRepository;
import com.springboot.ecom.repository.ProductImageRepository;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public Product addProductWithVendorAndCategory(Product product, User user, int categoryId) throws ResourceNotFoundException {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        product.setCategory(category);
        product.setUser(user);
        product.setFeaturedRequest(FeaturedRequest.NOTMADE);
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

    public Set<Product> findProductsByUser(int id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid vendor id "));

        Set<Product> products = productRepository.findProductsByVendor(id);

        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this vendor");
        }
        return products;
    }


    public Product updateProduct(Product existingProduct) {
        return productRepository.save(existingProduct);
    }

    public Product updateFeaturedStatus(Product product) {
        product.setFeaturedRequest(FeaturedRequest.PENDING);
        return productRepository.save(product);
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

    public Set<Product> getAllProductsByUser(int userId) throws ResourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid vendor"));

        Set<Product> products = productRepository.findProductsByVendor(userId);

        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this vendor");
        }
        return products;
    }

    private ProductImage addProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public ProductImage uploadImage(int productId, MultipartFile file) throws IOException, ResourceNotFoundException {
        System.out.println(file.getOriginalFilename());
        String location = "E:/Palash/Angular/ecommerce-frontend/public/images";
        Path path = Path.of(location, file.getOriginalFilename());
        //System.out.println(path.toString());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw e;
        }

        Product product = null;
        try {
            product = getProductById(productId);
        } catch (ResourceNotFoundException e) {
            throw e;
        }

        ProductImage pi = new ProductImage();
        pi.setFileName(file.getOriginalFilename());
        pi.setPath(path.toString());
        pi.setProduct(product);

        return addProductImage(pi);
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }
}
