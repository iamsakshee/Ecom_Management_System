package com.springboot.ecom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Category;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductImage;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.VendorService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/product/add/{vendorId}/{categoryId}")
	public ResponseEntity<?> addProduct(@PathVariable int vendorId, @PathVariable int categoryId,
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

//    @GetMapping("/products/vendor/all")
//    public ResponseEntity<?> getAllProductsByVendorId() throws ResourceNotFoundException {
//        Set<Product> products = productService.getAllProductsByVendor();
//        return ResponseEntity.ok(products);
//    }

	@GetMapping("/product/category/{categoryId}")
	public ResponseEntity<?> getProductsByCategory(@PathVariable int categoryId) throws ResourceNotFoundException {
	    // Fetch all products by category ID
	    List<Product> productList = productService.getProductsByCategoryId(categoryId);

	    // Initialize a list to hold ProductResponseDto objects
	    List<ProductResponseDto> listDto = new ArrayList<>();

	    // Loop through each product and map it to a ProductResponseDto
	    for (Product product : productList) {
	        // Fetch all images for the current product
	        List<ProductImage> imageList = productService.getAllProductImagesByProductId(product.getId());

	        // Map product to ProductResponseDto
	        ProductResponseDto dto = new ProductResponseDto();
	        dto.setId(product.getId());
	        dto.setName(product.getName());
	        dto.setStock(product.getStock());
	        dto.setPrice(product.getPrice());
	        dto.setDescription(product.getDescription());

	        // Filter and set product-specific images
	        List<ProductImage> iList = imageList.stream()
	                .filter(i -> i.getProduct().getId() == product.getId())
	                .toList();
	        dto.setImages(iList);

	        // Add the DTO to the list
	        listDto.add(dto);
	    }

	    // Return the list of ProductResponseDto objects
	    return ResponseEntity.ok(listDto);
	}


	@GetMapping("/api/product/all")
	public Page<ProductResponseDto> getAllProducts(@RequestParam(required = false, defaultValue = "0") String page,
			@RequestParam(required = false, defaultValue = "1000000") String size,
			@RequestParam(defaultValue = "") String searchKey) throws Exception{

		Pageable pageable = null;

		try {
			pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
		} catch (Exception e) {
			throw e;
		}

		Page<Product> pPage = productService.getAllProducts(pageable, searchKey);
		List<Product> pList = pPage.getContent();
		List<ProductImage> imageList = productService.getAllProductImages();

		List<ProductResponseDto> listDto = new ArrayList<>();
		for (Product p : pList) {
			ProductResponseDto dto = new ProductResponseDto();
			dto.setId(p.getId());
			dto.setName(p.getName());
			dto.setPrice(p.getPrice());
			dto.setDescription(p.getDescription());

			List<ProductImage> iList = imageList.stream().filter(i -> i.getProduct().getId() == p.getId()).toList();
//            System.out.println(iList);
			dto.setImages(iList);
//            System.out.println(dto);
			listDto.add(dto);
		}

		 return new PageImpl<>(listDto, pageable, pPage.getTotalElements());
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
		dto.setDescription(product.getDescription());

		List<ProductImage> iList = imageList.stream().filter(i -> i.getProduct().getId() == product.getId()).toList();
		dto.setImages(iList);
		listDto.add(dto);
		return ResponseEntity.ok(listDto);
	}

//    @GetMapping("/products-with-images/{vendorId}")
//    public ResponseEntity<?> getAllProductsAlongWithImagesForVendor(@PathVariable int vendorId) throws ResourceNotFoundException {
//
//        Set<Product> products = productService.getAllProductsByVendor();
//
//
//        List<ProductImage> allImages = productService.getAllProductImages();
//
//        List<ProductResponseDto> responseDtoList = new ArrayList<>();
//        for (Product product : products) {
//            ProductResponseDto dto = new ProductResponseDto();
//            dto.setId(product.getId());
//            dto.setName(product.getName());
//            dto.setPrice(product.getPrice());
//            dto.setStock(product.getStock());
//
//            List<ProductImage> productImages = allImages.stream()
//                    .filter(image -> image.getProduct().getId() == product.getId())
//                    .toList();
//            dto.setImages(productImages);
//
//            responseDtoList.add(dto);
//        }
//
//        return ResponseEntity.ok(responseDtoList);
//    }

	@GetMapping("/product-images/{productId}")
	public List<ProductImage> getProductImagesByProductId(@PathVariable int productId)
			throws ResourceNotFoundException {
		return productService.getAllProductImagesByProductId(productId);
	}

	@PutMapping("/product/update/status/{productId}")
	public ResponseEntity<?> updateFeaturedProduct(@PathVariable int productId) throws ResourceNotFoundException {
		Product existingProduct = productService.getProductById(productId);

		return ResponseEntity.ok(existingProduct);
	}

	@PutMapping("/product/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct)
			throws ResourceNotFoundException {
		Product existingProduct = productService.getProductById(productId);

		if (updatedProduct.getName() != null) {
			existingProduct.setName(updatedProduct.getName());
		}
		if (updatedProduct.getDescription() != null) {
			existingProduct.setDescription(updatedProduct.getDescription());
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