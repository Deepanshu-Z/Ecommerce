package com.ecommerce.ecom.Payload;

import java.util.List;

public class CategoryResponse {
    private List<CategoryDTO> categoryResponse;

    public List<CategoryDTO> getCategoryResponse() {
        return categoryResponse;
    }

    public void setCategoryResponse(List<CategoryDTO> categoryResponse) {
        this.categoryResponse = categoryResponse;
    }
}
