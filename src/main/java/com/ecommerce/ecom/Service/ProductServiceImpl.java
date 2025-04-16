package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Controller.ProductController;
import com.ecommerce.ecom.ExceptionHandler.ApiException;
import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.ProductDTO;
import com.ecommerce.ecom.Payload.ProductResponse;
import com.ecommerce.ecom.Repository.CategoryRepository;
import com.ecommerce.ecom.Repository.ProductRepository;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    //////////////////////////ADDING PRODUCT/////////////////////////////////////
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category NOT FOUND!"));
        Product product = modelMapper.map(productDTO, Product.class);
        Optional<Product> productExist = productRepository.findByProductName(product.getProductName());
        if(productExist.isPresent()) throw new ApiException(product.getProductName());

        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() -
                ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


    //////////////////////////GET ALL PRODUCTs/////////////////////////////////////
    public ProductResponse getAllProducts(){
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) throw new ResourceNotFoundException("No products present in the Database!");
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTO);
        return response;
    }

    //////////////////////////GET PRODUCTs by CATEGORY/////////////////////////////////////
    public ProductResponse getCategoryProduct(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("No category available"));
        List<Product> products = productRepository.findByCategoryOrderByPrice(category);
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTO);
        return response;
    }

    //////////////////////////GET PRODUCTs by KEY/////////////////////////////////////
    public ProductResponse getKeyProduct(String key) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + key + "%");
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse response = new ProductResponse();
        response.setContent(productDTO);
        return response;
    }
    ////////////////////////// UPDATE PRODUCT /////////////////////////////////
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Optional<Product> existingProductOptional = productRepository.findById(productId);

        // If product not found, throw exception
        if (existingProductOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product with ID " + productId + " does not exist");
        }

        // Get the existing product
        Product existingProduct = existingProductOptional.get();

        // Update fields — this prevents overwriting the ID or accidentally mapping nulls
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setQuantity(productDTO.getQuantity());
        // Add more fields as needed (price, category, etc.)

        // Save updated product
        Product updatedProduct = productRepository.save(existingProduct);

        // Return updated DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    //////////////////DELETE PRODUCT/////////////////////////////////////////
    public ProductDTO deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow( () -> new ResourceNotFoundException("Product does not found to be deleted"));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productRepository.delete(product);
        return productDTO;
    }


    ///////////////UPLOAD IMAGE TO SERVER///////////////////////////////////
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get the product from db
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID is not valid or product is not present"));

        //upload it to server
        //get the filename in which it is uploaded
        String path = "images/";
        String filename = uploadImage(path, image);

        //save it
        product.setImage(filename);
        productRepository.save(product);

        //and return dto
        return modelMapper.map(product, ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile image) throws IOException {
        //fetch the image file name
        String imageFileName = image.getOriginalFilename();

        //generate a unique file name so name does not clashes
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId.concat(imageFileName.substring(imageFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;
        //check if path exist or else create
        File folder = new File(path);
        if(!folder.exists()) folder.mkdir();
        //upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));
        //return file name
        return fileName;
    }
}
