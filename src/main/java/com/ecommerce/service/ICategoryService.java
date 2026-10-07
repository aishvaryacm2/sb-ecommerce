package com.ecommerce.service;

import com.ecommerce.pojos.CategoryPOJO;
import com.ecommerce.response.CategoryResponse;


public interface ICategoryService {

    CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    void createCategory(CategoryPOJO categoryPOJO);

    String removeCategory(long id);

    CategoryResponse updateCategory(long id, CategoryPOJO categoryPOJO);
}
