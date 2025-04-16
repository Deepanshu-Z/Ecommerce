package com.ecommerce.ecom.Controller;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Payload.ProductResponse;
import com.ecommerce.ecom.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

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
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @RequestParam Long categoryId){

        ProductDTO savedProductDTO = productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }


    //////////////////////////GET ALL PRODUCT/////////////////////////////////////
    @GetMapping("/admin/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //////////////////////////GET PRODUCT BY CATEGORY/////////////////////////////////////
    @GetMapping("/admin/category/get/{categoryId}/product")
    public ResponseEntity<ProductResponse> getCategoryProduct(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.getCategoryProduct(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    //////////////////////////GET PRODUCT BY KEY/////////////////////////////////////
    @GetMapping("/admin/category/get/product/{key}")
    public ResponseEntity<ProductResponse> getCategoryProduct(@PathVariable String key){
        ProductResponse productResponse =   productService.getKeyProduct(key);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    //////////////////////////UPDATE PRODUCT/////////////////////////////////////
    @PutMapping("/admin/category/update/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO =  productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    //////////////////////////DELETE PRODUCT/////////////////////////////////////
    @DeleteMapping("/admin/category/delete/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO =  productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //////////////////////////UPLOAD IMAGE /////////////////////////////////////
    @PutMapping("/admin/category/update/image/{productId}")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                  @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO productDTO = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
