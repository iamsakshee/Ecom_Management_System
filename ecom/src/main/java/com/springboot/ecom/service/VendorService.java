package com.springboot.ecom.service;

import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.repository.UserRepository;
import com.springboot.ecom.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

//    public Vendor saveVendor(Vendor vendor) {
//        return vendorRepository.save(vendor);
//    }



//    public Vendor addVendor(Vendor vendor) throws ResourceNotFoundException, DuplicateEntryException {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        if (vendorRepository.existsByEmail(vendor.getEmail())) {
//            throw new DuplicateEntryException("Email already exists" );
//        }
//
//        if (vendorRepository.existsByPhone(vendor.getPhone())) {
//            throw new DuplicateEntryException("Phone number already exists");
//        }
//
//        if (vendorRepository.existsByGstNumber(vendor.getGstNumber())) {
//            throw new DuplicateEntryException("GST number already exists");
//        }
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
//
//        vendor.setRegistrationDate(LocalDate.now());
//        vendor.set(user);
//
//        return vendorRepository.save(vendor);
//    }


//    public List<Vendor> getAllVendors() {
//        return vendorRepository.findAll();
//    }
//
//    public Vendor getVendorById(int id) throws ResourceNotFoundException {
//        Optional<Vendor> optional = vendorRepository.findById(id);
//        if (optional.isEmpty()) {
//            throw new ResourceNotFoundException("Vendor id invalid");
//        }
//        return optional.get();
//    }

//    public void deleteById(int id) {
//        vendorRepository.deleteById(id);
//    }
}
