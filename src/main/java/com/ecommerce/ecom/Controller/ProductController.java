package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //////////////////////////ADDING PRODUCT/////////////////////////////////////
    @PostMapping("/admin/category/{categoryName}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @PathVariable String categoryName){
        return productService.addProduct(product, categoryName);
    }
}
