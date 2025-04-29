package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    public List<CartDTO> getAllCarts();
}
