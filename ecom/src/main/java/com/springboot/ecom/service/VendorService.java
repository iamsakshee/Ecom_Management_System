package com.springboot.ecom.service;

import com.springboot.ecom.exception.DuplicateEntryException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.UserRepository;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }


    public Vendor addVendor(Vendor vendor) throws ResourceNotFoundException, DuplicateEntryException {

        if (vendorRepository.existsByUser(vendor.getUser())) {
            throw new DuplicateEntryException("A vendor is already associated with this user.");
        }
        if (vendorRepository.existsByEmail(vendor.getEmail())) {
            throw new DuplicateEntryException("Email already exists");
        }

        if (vendorRepository.existsByPhone(vendor.getPhone())) {
            throw new DuplicateEntryException("Phone number already exists");
        }

        if (vendorRepository.existsByGstNumber(vendor.getGstNumber())) {
            throw new DuplicateEntryException("GST number already exists");
        }

        vendor.setRegistrationDate(LocalDate.now());

        return vendorRepository.save(vendor);
    }


    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(int id) throws ResourceNotFoundException {

        return vendorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("invalid vendor id")
        );
    }

    public void deleteById(int id) {
        vendorRepository.deleteById(id);
    }

    public Vendor getVendorDetailsByUsername(String username) {
        return vendorRepository.getVendorDetailsByUsername(username);
    }
}
