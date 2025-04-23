package com.ecommerce.ecom.Payload;

public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cartDto;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartDTO getCartDto() {
        return cartDto;
    }

    public void setCartDto(CartDTO cartDto) {
        this.cartDto = cartDto;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
