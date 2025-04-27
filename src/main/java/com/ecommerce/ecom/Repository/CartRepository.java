package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByEmail(String email);
}
