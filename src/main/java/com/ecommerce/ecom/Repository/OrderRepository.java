package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
