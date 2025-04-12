package com.ecommerce.ecom.Service;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Repository.CategoryRepository;
import com.ecommerce.ecom.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
       Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return "Category deleted successfully";
        } else {
            throw new RuntimeException("Category not found to be deleted");
        }
    }

    @Override
    public String updateCategory(Long categoryid){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryid);

        if(optionalCategory.isPresent()){
            Category updateCategory = optionalCategory.get();
            updateCategory.setCategoryName(updateCategory.getCategoryName() + "updated");
            categoryRepository.save(updateCategory);
            return "Updated Successfully!";

        }throw new RuntimeException("Category not found to be updated");
    }
}
