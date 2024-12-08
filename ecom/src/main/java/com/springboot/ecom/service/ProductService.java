package com.springboot.ecom.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecom.enums.FeaturedRequest;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductImage;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.CategoryRepository;
import com.springboot.ecom.repository.ProductImageRepository;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.UserRepository;
import com.springboot.ecom.repository.VendorRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private UserRepository userRepository;

    public Product addProductWithVendorAndCategory(Product product, Vendor vendor, Category category) throws ResourceNotFoundException {

        product.setCategory(category);
        product.setVendor(vendor);
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable, String searchKey) {
    	if(searchKey.equals(""))
    	{
    		 return productRepository.findAll(pageable);
    	}else
    	{
    		return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
    	}
    	
       
    }

    public Product getProductById(int id) throws ResourceNotFoundException {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Product id invalid");
        }
        return optional.get();
    }

    public Product updateProduct(Product existingProduct) {
        return productRepository.save(existingProduct);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategoryId(int categoryId) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category invalid "));

        List<Product> products = productRepository.getAllProductsByCategoryId(categoryId);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for this category ");
        }
        return products;
    }

//    public Set<Product> getAllProductsByVendor() throws ResourceNotFoundException {
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        User user = userService.findByUsername(username);
//
//        Set<Product> products = productRepository.findProductsByVendor(username);
//
//        if (products == null || products.isEmpty()) {
//            throw new ResourceNotFoundException("No products found for this vendor");
//        }
//        return products;
//    }

    public ProductImage addProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public ProductImage uploadImage(int productId, MultipartFile file) throws IOException, ResourceNotFoundException {
        System.out.println(file.getOriginalFilename());
        String location = "C:/Users/saksh/OneDrive/Desktop/java_angular_fsd/Angular/ecom-app/public/images";
        Path path = Path.of(location, file.getOriginalFilename());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


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

    public List<ProductImage> getAllProductImagesByProductId(int productId) throws ResourceNotFoundException {
        List<ProductImage> productImages = productImageRepository.findProductImagesByProductId(productId);
        if (productImages.isEmpty()) {
            throw new ResourceNotFoundException("no images found for this product");
        }
        return productImages;
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

	public List<Product> getAllProducts() {
		
		return null;
	}

	
}