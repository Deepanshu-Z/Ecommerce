package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Payload.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
