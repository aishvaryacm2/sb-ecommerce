package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.response.CategoryResponse;


public interface ICategoryService {

    CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    void createCategory(CategoryDTO categoryDTO);

    String removeCategory(long id);

    CategoryResponse updateCategory(long id, CategoryDTO categoryDTO);
}
