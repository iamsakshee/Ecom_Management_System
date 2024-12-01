package com.springboot.ecom.service;

import com.springboot.ecom.enums.FeaturedRequest;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getAllProducts() throws ResourceNotFoundException {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }

        return products;
    }
    public List<Product> getAllFeaturedProducts() throws ResourceNotFoundException {
        List<Product> products = productRepository.getAllFeaturedProducts();
        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No Featured Products request found");
        }
        return products;
    }

    public User updateUserStatus(int id, boolean status) throws ResourceNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty())
            throw new ResourceNotFoundException("UserId is Invalid");

        User user = optional.get();
        user.setEnabled(status);
        return userRepository.save(user);
    }

    public Product updateFeaturedStatus(Product product) {
        product.setFeaturedRequest(FeaturedRequest.APPROVED);
        return productRepository.save(product);
    }
}
