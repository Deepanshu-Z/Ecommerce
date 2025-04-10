package com.ecommerce.ecom.Service;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public String deleteCategory(Long categoryId){
        Optional<Category> optionalCategory = categories.stream()
                .filter(e -> e.getCategoryId().equals(categoryId))
                .findFirst();

        if (optionalCategory.isPresent()) {
            categories.remove(optionalCategory.get());
            return "Category deleted successfully";
        } else {
            throw new RuntimeException("Category not found to be deleted");
        }
    }

    @Override
    public String updateCategory(Long categoryid){
        Optional<Category> foundCategory = categories.stream()
                .filter(e -> e.getCategoryId().equals(categoryid))
                .findFirst();
        if(foundCategory.isPresent()){
            Category updateCategory = foundCategory.get();
            updateCategory.setCategoryName(updateCategory.getCategoryName() + "Updated");
            return "Updated Successfully!";

        }throw new RuntimeException("Category not found to be updated");
    }
}
