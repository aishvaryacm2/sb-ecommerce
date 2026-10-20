package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    public ProductResponse addProduct(ProductDTO productDTO, Long categoryId);
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    public ProductResponse searchByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse updateProductById(Long productId, ProductDTO productDTO);

    ProductResponse removeProduct(Long productId);

    ProductResponse updateProductImage(Long productId, MultipartFile file) throws IOException;
}
