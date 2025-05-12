package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Payload.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts(String key, String category, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getCategoryProduct(Long categoryId, Integer pageNumber,  Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getKeyProduct(String key, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
