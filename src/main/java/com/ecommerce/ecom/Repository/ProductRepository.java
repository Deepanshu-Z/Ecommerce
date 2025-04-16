package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);

    List<Product> findByCategoryOrderByPrice(Category category);
}
