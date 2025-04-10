package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Service.CategoryService;
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
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        try{
            categoryService.addCategory(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Problem creating category: " + e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAll(){
       List<Category> categories = categoryService.getAll();

       if(categories.isEmpty()){
           return ResponseEntity.noContent().build();
       }else{
           return new ResponseEntity<>(categories, HttpStatus.OK);
       }

    }


}
