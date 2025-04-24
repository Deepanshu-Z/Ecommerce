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

    public CartDTO addProductToCart(Long productId, Integer quantity){
        Cart cart = createCart();
        Optional<Product> productExist = productRepository.findById(productId);
        if(productExist.isEmpty()) throw new RuntimeException("Product Not Available");

        Product productDetails = productExist.get();
        CartItems cartItems = new CartItems();
        cartItems.setCart(cart);
        cartItems.setDiscount(10.00);
        cartItems.setProduct(productDetails);
        cartItems.setQuantity(quantity);
        cartItems.setProductPrice((productDetails.getPrice() * quantity) - cartItems.getDiscount());
        cartItemRepository.save(cartItems);
        List<CartItems> cartItemsList = cart.getCartItems();
        cartItemsList.add(cartItems);
        cartRepository.save(cart);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        return cartDTO;
    }

    private Cart createCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Cart cart = userDetails.getCart();
        if(cart == null){
            cart = new Cart();
            cart.setUser(userDetails);
            cart.setCartItems(new ArrayList<>());
            userDetails.setCart(cart);
            userRepository.save(userDetails);
        }
        return cart;
        }
}
