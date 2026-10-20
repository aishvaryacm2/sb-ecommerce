package com.ecommerce.wrapper;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;


public class ProductWrapper extends ProductDTO {
    private Product product;

    public ProductWrapper(Product product) {
        this.product = product;
    }

    @Override
    public Long getId() {
        return product.getProductId();
    }

    @Override
    public String getProductName() {
        return product.getProductName();
    }

    @Override
    public String getImage() {
        return product.getImage();
    }

    @Override
    public Integer getQuantity() {
        return product.getQuantity();
    }

    @Override
    public Double getPrice() {
        return product.getPrice();
    }

    @Override
    public Double getDiscount() {
        return product.getDiscount();
    }

    @Override
    public Double getSpecialPrice() {
        return product.getSpecialPrice();
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }
}
