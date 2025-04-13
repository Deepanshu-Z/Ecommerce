package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Payload.CategoryResponse;
import com.ecommerce.ecom.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping("/admin/category")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category){
        categoryService.addCategory(category);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAll(){
       CategoryResponse categories = categoryService.getAll();
       return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String message = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryid}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryid){
            String message = categoryService.updateCategory(categoryid);
            return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
