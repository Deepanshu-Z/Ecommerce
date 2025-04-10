package com.ecommerce.ecom.Service;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }
}
