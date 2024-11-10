package com.springboot.ecom.service;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public Vendor addVendor(Vendor vendor) {
        vendor.setRegistrationDate(LocalDate.now());
        return vendorRepository.save(vendor);
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
}
