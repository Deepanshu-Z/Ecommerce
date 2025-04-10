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

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try{
            String message = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete category: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/admin/categories/{categoryid}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryid){
        try{
            String message = categoryService.updateCategory(categoryid);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Category not found please try again" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
