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

    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not found"));

        CartItems existingCartItem = cartItemRepository.findCartItemByProductIdAndCartId(product.getProductId(), cart.getCartId());

        if (existingCartItem != null)
            throw new ApiException(product.getProductName());

        if (product.getQuantity() == 0)
            throw new ApiException(product.getProductName() + " is out of stock", product.getProductName());

        if (product.getQuantity() < quantity)
            throw new ApiException("Not enough stock. Order must be less than or equal to " + product.getQuantity(), product.getProductName());

        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setDiscount(product.getDiscount());
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        // ✅ FIX: Add the newly saved cartItem into cart's cartItems list manually
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        cart.getCartItems().add(cartItem);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOList = cart.getCartItems().stream()
                .map(item -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                }).toList();

        cartDTO.setProducts(productDTOList);

        return cartDTO;
    }

    private Cart createCart() {

        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

        if(userCart != null) return userCart;

        Cart newCart = new Cart();
        newCart.setUser(authUtil.loggedInUser());
        newCart.setTotalPrice(0.0);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);

    }
}
