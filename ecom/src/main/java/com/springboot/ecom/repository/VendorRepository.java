package com.springboot.ecom.repository;

import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByGstNumber(String gstNumber);

    boolean existsByUser(User user);


    @Query("select v from Vendor v where v.user.username = :username")
    Vendor getVendorDetailsByUsername(String username);
}
