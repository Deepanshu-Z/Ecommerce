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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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
    public CategoryResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Category> pageDetails = categoryRepository.findAll(pageable);
        List<Category> categories =  pageDetails.getContent();

        if(categories.isEmpty()){
            throw new ResourceNotFoundException("No categories present, please add some categories first!");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(cat -> modelMapper.map(cat, CategoryDTO.class))
                .toList();

        CategoryResponse response = new CategoryResponse();
        response.setCategoryResponse(categoryDTOS);
        response.setPageNumber(pageDetails.getNumber());
        response.setPageSize(pageDetails.getSize());
        response.setTotalElements(pageDetails.getTotalElements());
        response.setTotalPages(pageDetails.getTotalPages());
        response.setLastPage(pageDetails.isLast());
        return response;
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category isAlreadyPresent = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if(isAlreadyPresent != null){
            throw new ApiException(categoryDTO.getCategoryName());
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId){
       Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
            categoryRepository.delete(category);
            return categoryDTO;
        } else {
            throw new ResourceNotFoundException("Category", categoryId);
        }
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryid){
        Category category = categoryRepository.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        category.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }
}
