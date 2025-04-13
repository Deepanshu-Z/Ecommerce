package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAll(Integer pageNumber, Integer pageSize);
    public void addCategory(CategoryDTO categoryDTO);
    public CategoryDTO deleteCategory(Long categoryId);
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryid);

}
