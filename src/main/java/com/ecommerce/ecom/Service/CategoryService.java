package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public List<Category> getAll();
    public void addCategory(Category category);
    public String deleteCategory(Long categoryId);
    public String updateCategory(Long categoryid);

}
