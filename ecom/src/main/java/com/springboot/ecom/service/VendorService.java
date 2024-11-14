package com.springboot.ecom.service;

import com.springboot.ecom.enums.Role;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.UserRepository;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor addVendor(Vendor vendor) throws ResourceNotFoundException {
        vendor.setRegistrationDate(LocalDate.now());
        try {
            return vendorRepository.save(vendor);
        } catch (Exception e) {
            throw new ResourceNotFoundException("username already exists");
        }
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(int id) throws ResourceNotFoundException {
        Optional<Vendor> optional = vendorRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Vendor id invalid");
        }
        return optional.get();
    }

    public void deleteById(int id) {
        vendorRepository.deleteById(id);
    }
}
