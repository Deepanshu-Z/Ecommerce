package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Payload.CartDTO;
import com.ecommerce.ecom.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }
}
