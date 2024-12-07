package com.springboot.ecom.repository;

import com.springboot.ecom.dto.VendorOrderProductDto;
import com.springboot.ecom.enums.OrderStatus;
import com.springboot.ecom.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    @Query("select op.order.id as order_id, op.order.orderDate as order_date, " +
            "c.name as customer_name, c.email as customer_email, " +
            "p.name as product_name, p.price as product_price, " +
            "op.quantity as quantity, op.order.orderStatus as order_status " +
            "from OrderProduct op " +
            "JOIN op.order o " +
            "JOIN op.product p " +
            "JOIN o.customer c " +
            "WHERE p.vendor.id = :vendorId AND o.orderStatus = :orderStatus")
    List<Object[]> findProductsForVendorWithStatus(@Param("vendorId") int vendorId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("select op.order.id as order_id, op.order.orderDate as order_date, " +
            "c.name as customer_name, c.email as customer_email, " +
            "p.name as product_name, p.price as product_price, " +
            "op.quantity as quantity, op.order.orderStatus as order_status " +
            "from OrderProduct op " +
            "JOIN op.order o " +
            "JOIN op.product p " +
            "JOIN o.customer c " +
            "WHERE p.vendor.id = :vendorId")
    List<Object[]> findAllOrdersForVendor(@Param("vendorId") int vendorId);
}
