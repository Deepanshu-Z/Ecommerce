package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.ecom.Model.Cart;
import com.ecommerce.ecom.Model.CartItems;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Model.User;
import com.ecommerce.ecom.Payload.CartDTO;
import com.ecommerce.ecom.Repository.CartItemRepository;
import com.ecommerce.ecom.Repository.CartRepository;
import com.ecommerce.ecom.Repository.ProductRepository;
import com.ecommerce.ecom.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthUtil authUtil;
    
    public CartDTO addProductToCart(Long productId, Integer quantity){
        Cart cart = createCart();



        return cartDTO;
    }

    private Cart createCart() {

        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

        if(userCart != null) return userCart;

        Cart newCart = new Cart();
        newCart.setUser(authUtil.loggedInUser());
        newCart.setTotalPrice(0.0);
        return cartRepository.save(newCart);

    }
}
