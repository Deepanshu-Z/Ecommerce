package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Payload.CartDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getUserCart(String email, Long cartId);

    @Transactional
    CartDTO updateQuantity(Long productId, Integer quantity);

    String removeProduct(Long cartId, Long productId);
}
