package com.ecommerce.service;

import com.ecommerce.model.Category;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ICategoryService {

    List<com.ecommerce.model.Category> getCategories();

    void createCategory(Category category);

    String removeCategory(long id);

    Category updateCategory(long id, Category category);
}
