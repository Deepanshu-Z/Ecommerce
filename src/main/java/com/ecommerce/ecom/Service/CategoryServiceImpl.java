package com.ecommerce.ecom.Service;
import com.ecommerce.ecom.Config.AppConfig;
import com.ecommerce.ecom.ExceptionHandler.ApiException;
import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.ecom.Model.Category;
import com.ecommerce.ecom.Payload.CategoryDTO;
import com.ecommerce.ecom.Payload.CategoryResponse;
import com.ecommerce.ecom.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse getAll() {
        List<Category> categories =  categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("No categories present, please add some categories first!");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(cat -> modelMapper.map(cat, CategoryDTO.class))
                .toList();

        CategoryResponse response = new CategoryResponse();
        response.setCategoryResponse(categoryDTOS);
        return response;
    }

    @Override
    public void addCategory(Category category) {
        Category isAlreadyPresent = categoryRepository.findByCategoryName(category.getCategoryName());
        if(isAlreadyPresent != null){
            throw new ApiException(category.getCategoryName());
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
       Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return "Category deleted successfully";
        } else {
            throw new ResourceNotFoundException("Category", categoryId);
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

        }throw new ResourceNotFoundException("Category", categoryid);
    }
}
