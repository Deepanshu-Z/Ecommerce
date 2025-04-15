package com.ecommerce.ecom.Payload;

import java.util.List;

public class ProductResponse {
    List<ProductDTO> productDTOS;

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }
}
