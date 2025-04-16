package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Payload.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts();

    ProductResponse getCategoryProduct(Long categoryId);

    ProductResponse getKeyProduct(String key);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);
}
