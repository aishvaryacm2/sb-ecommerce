package com.ecommerce.service;

import com.ecommerce.constants.ResponseMessage;
import com.ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceServiceImpl implements ICategoryService {

    private List<Category> categoryList = new ArrayList<>();

    private long id = 0L;

    @Override
    public List<Category> getCategories() {
        try {
            return categoryList;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ResponseMessage.Category.UNABLE_TO_FETCH);
        }
    }

    @Override
    public void createCategory(Category category) {
        try{
        category.setId(incrementId());
        categoryList.add(category);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.Category.UNABLE_TO_ADD);
        }


    }

    @Override
    public String removeCategory(long id) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.Category.MANDATORY_FIELD);
        }
        if (!categoryList.isEmpty()) {
            Category category = categoryList.stream().filter(category1 -> category1.getId() == id)
                    .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not found"));
            categoryList.remove(category);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return "Successfully removed category with id " + id;
    }

    @Override
    public Category updateCategory(long id, Category newCategory) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.Category.MANDATORY_FIELD);
        }
        if (!categoryList.isEmpty()) {
            Category existingCategory = categoryList.stream().filter(category1 -> category1.getId() == id)
                    .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not found"));
            existingCategory.setCategoryName(newCategory.getCategoryName());
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    private long incrementId() {
        return ++id;
    }
}
