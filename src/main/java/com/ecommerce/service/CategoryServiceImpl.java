package com.ecommerce.service;

import com.ecommerce.constants.ResponseMessage;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.Category.UNABLE_TO_FETCH);
        }
    }

    @Override
    public void createCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.Category.UNABLE_TO_ADD);
        }


    }

    @Override
    public String removeCategory(long id) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.Category.MANDATORY_FIELD);
        }
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Resource not found"
                        ));
        categoryRepository.delete(existingCategory);

        return "Successfully removed category with id " + id;

    }


    @Override
    public Category updateCategory(long id, Category newCategory) {

        if (id == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ResponseMessage.Category.MANDATORY_FIELD
            );
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Resource not found"
                        ));

        existingCategory.setCategoryName(newCategory.getCategoryName());

        return categoryRepository.save(existingCategory);
    }

}
