package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByProductName(String productName);

    List<Product> findByCategoryOrderByPrice(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String key);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String key, Pageable pageable);
}
