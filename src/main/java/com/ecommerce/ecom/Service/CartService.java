package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
