package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
