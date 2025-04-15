package com.ecommerce.ecom.Payload;

import java.util.List;

public class ProductResponse {
    List<ProductDTO> content;

    public List<ProductDTO> getContent() {
        return content;
    }

    public void setContent(List<ProductDTO> content) {
        this.content = content;
    }
}
