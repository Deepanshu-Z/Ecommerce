package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.ExceptionHandler.ApiException;
import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.ecom.Model.Cart;
import com.ecommerce.ecom.Model.CartItems;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Model.User;
import com.ecommerce.ecom.Payload.CartDTO;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Repository.CartItemRepository;
import com.ecommerce.ecom.Repository.CartRepository;
import com.ecommerce.ecom.Repository.ProductRepository;
import com.ecommerce.ecom.Repository.UserRepository;
import com.ecommerce.ecom.Util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not found"));

        CartItems cartItem = cartItemRepository.findCartItemByProductIdAndCartId(product.getProductId(), cart.getCartId());

        if(cartItem != null) throw new ApiException(product.getProductName());

        if(product.getQuantity() == 0) throw new ApiException(product.getProductName() + " is out of stock", product.getProductName());

        if(product.getQuantity() < quantity) throw new ApiException("Not enough stock. Order must be less than or equal to" +
                quantity, product.getProductName());

        CartItems cartItems = new CartItems();
        cartItems.setCart(cart);
        cartItems.setProduct(product);
        cartItems.setProductPrice(product.getSpecialPrice());
        cartItems.setDiscount(product.getDiscount());
        cartItems.setQuantity(quantity);
        cartItemRepository.save(cartItems);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItems> cartItemsList = cart.getCartItems();
        Stream<ProductDTO> productDTO = cartItemsList.stream()
                .map(item -> {
                   ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                });

        cartDTO.setProducts(productDTO.toList());
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
