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
import jakarta.transaction.Transactional;
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
public class CartServiceImpl implements CartService {

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

    @Override
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

        if (userCart != null) return userCart;

        Cart newCart = new Cart();
        newCart.setUser(authUtil.loggedInUser());
        newCart.setTotalPrice(0.0);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);

    }

    /////////////////GET ALL CARTS/////////////////////////////
    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if(carts.isEmpty()) throw new ApiException("No carts items present!");

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<ProductDTO> productDTO = cart.getCartItems().stream()
                    .map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).toList();
            cartDTO.setProducts(productDTO);
            return cartDTO;
        }).toList();

        return cartDTOs;
    }

    /////////////////GET CART BY ID/////////////////////////////
    @Override
    public CartDTO getUserCart(String email, Long cartId){
        Cart cart = cartRepository.findCartByEmail(email);
        if(cart == null) throw new ApiException("Cart not found!");

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(items -> items.getProduct().setQuantity(items.getQuantity()));
        List<ProductDTO> productDTO = cart.getCartItems().stream()
                .map(cartItems -> modelMapper.map(cartItems.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(productDTO);
        return cartDTO;
    }


    ////////////UPDATE CART QUANTITY/////////////////////////
    @Transactional
    @Override
    public CartDTO updateQuantity(Long productId, Integer quantity){
        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        if(cart == null) throw new ApiException("Cart is not present");

        Long cartId = cart.getCartId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not available"));

        if (product.getQuantity() == 0) {
            throw new ApiException("Product is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new ApiException("Please, make an order less than or equal to the quantity " + product.getQuantity());
        }

        CartItems cartItems = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartItems == null) {
            throw new ApiException("Product not available in the cart!!!");
        }

        cartItems.setProductPrice(product.getSpecialPrice());
        cartItems.setQuantity(cartItems.getQuantity() + quantity);
        cartItems.setDiscount(product.getDiscount());
        cart.setTotalPrice(cart.getTotalPrice() + (cartItems.getProductPrice() * quantity));
        cartRepository.save(cart);

        CartItems updatedItem = cartItemRepository.save(cartItems);
        if(updatedItem.getQuantity() == 0){
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItems> cartItemsList = cart.getCartItems();
        Stream<ProductDTO> productStream = cartItemsList.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

        ////////////////DELETE PRODUCT FROM CART//////////////////
        @Transactional
        @Override
        public String removeProduct(Long cartId, Long productId) {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new ResourceNotFoundException("cart does not found"));

            CartItems cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

            if (cartItem == null) {
                throw new ResourceNotFoundException("cart Item missing");
            }

            cart.setTotalPrice(cart.getTotalPrice() -
                    (cartItem.getProductPrice() * cartItem.getQuantity()));

            cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

            return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
        }


}

