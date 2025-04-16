package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Payload.ProductResponse;
import com.ecommerce.ecom.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //////////////////////////ADDING PRODUCT/////////////////////////////////////
    @PostMapping("/admin/category/add/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @RequestParam Long categoryId){

        ProductDTO productDTO = productService.addProduct(categoryId,product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }


    //////////////////////////GET ALL PRODUCT/////////////////////////////////////
    @GetMapping("/admin/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //////////////////////////GET PRODUCT BY CATEGORY/////////////////////////////////////
    @GetMapping("/admin/category/get/{categoryId}/product")
    public List<Product> getCategoryProduct(@PathVariable Long categoryId){
         return productService.getCategoryProduct(categoryId);
    }

}
