package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Cart;
import com.ecommerce.ecom.Payload.CartDTO;
import com.ecommerce.ecom.Repository.CartRepository;
import com.ecommerce.ecom.Service.CartService;
import com.ecommerce.ecom.Util.AuthUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CartRepository cartRepository;

    ////////////ADD PRODUCTS TO CART////////////////////////////////////
    @PostMapping("/public/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                        @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    ////////////GET ALL CARTS////////////////////////////////////
    @GetMapping("/public/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK) ;
    }

    ////////////GET CART BY USER ID////////////////////////////////////
    @GetMapping("/public/user/carts")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long userId){
        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        Long cartId = cart.getCartId();
        return new ResponseEntity<>(cartService.getUserCart(email, cartId), HttpStatus.OK) ;
    }

    //////////////UPDATE CART QUANTITY/////////////////////////
    @PutMapping("/public/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long productId,
                                           @PathVariable String operation){
        CartDTO cartDTO = cartService.updateQuantity(productId, operation.equalsIgnoreCase("delete")?-1:1);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    ///////DELETE PRODUCT FROM CART///////////////////////////
    @DeleteMapping("/public/cart/{cartId}/product/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId,
                                                @PathVariable Long cartId){
        String status = cartService.removeProduct(cartId, productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
