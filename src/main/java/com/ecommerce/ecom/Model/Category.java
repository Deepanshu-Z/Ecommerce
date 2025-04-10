package com.ecommerce.ecom.Model;


public class Category {
    private Long categoryId;
    private String categoryName;

    public Category(){}
    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public long getCategoryId(){
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
