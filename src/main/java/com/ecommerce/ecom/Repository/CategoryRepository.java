package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
