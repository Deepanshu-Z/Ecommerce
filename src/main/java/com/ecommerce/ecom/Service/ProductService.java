package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.ProductDTO;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    public ResponseEntity<ProductDTO> addProduct(Product product, String categoryName);

}
