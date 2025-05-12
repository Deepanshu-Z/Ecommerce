package com.ecommerce.ecom.Controller;
import com.ecommerce.ecom.Config.AppConstants;
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
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(defaultValue = AppConstants.page_number, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.page_size, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.products_sort_by, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.sort_order, required = false) String sortOrder
    ){
        ProductResponse response = productService.getAllProducts(key, category, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //////////////////////////GET PRODUCT BY CATEGORY/////////////////////////////////////
    @GetMapping("/public/category/get/{categoryId}/product")
    public ResponseEntity<ProductResponse> getCategoryProduct(@PathVariable Long categoryId,
                                                              @RequestParam(defaultValue = AppConstants.page_number, required = false) Integer pageNumber,
                                                              @RequestParam(defaultValue = AppConstants.page_size, required = false) Integer pageSize,
                                                              @RequestParam(defaultValue = AppConstants.products_sort_by, required = false) String sortBy,
                                                              @RequestParam(defaultValue = AppConstants.sort_order, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getCategoryProduct(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    //////////////////////////GET PRODUCT BY KEY/////////////////////////////////////
    @GetMapping("/public/category/get/product/{key}")
    public ResponseEntity<ProductResponse> getCategoryProduct(@PathVariable String key,
                                                              @RequestParam(defaultValue = AppConstants.page_number, required = false) Integer pageNumber,
                                                              @RequestParam(defaultValue = AppConstants.page_size, required = false) Integer pageSize,
                                                              @RequestParam(defaultValue = AppConstants.products_sort_by, required = false) String sortBy,
                                                              @RequestParam(defaultValue = AppConstants.sort_order, required = false) String sortOrder
    ){
        ProductResponse productResponse =   productService.getKeyProduct(key, pageNumber, pageSize, sortBy, sortOrder);
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

    ////////////////////////UPLOAD IMAGE /////////////////////////////////////
    @PutMapping("/public/category/update/image/{productId}")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                  @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO productDTO = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
