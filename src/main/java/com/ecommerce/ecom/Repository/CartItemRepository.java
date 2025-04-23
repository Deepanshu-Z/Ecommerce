package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
