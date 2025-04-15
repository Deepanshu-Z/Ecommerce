package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Controller.ProductController;
import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Repository.CategoryRepository;
import com.ecommerce.ecom.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;
    ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //////////////////////////ADDING PRODUCT/////////////////////////////////////
    public ResponseEntity<ProductDTO> addProduct(Product product, String categoryName) {
        Optional<Product> optionalProduct = productRepository.findByProductName(product.getProductName());

        if(optionalProduct.isPresent()){
            throw new ResourceNotFoundException("product already exist!");
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        product.setCategory(category);
        productRepository.save(product);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }
}
