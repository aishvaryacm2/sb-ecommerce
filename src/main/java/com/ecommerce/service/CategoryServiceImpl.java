package com.ecommerce.service;

import com.ecommerce.constants.ResponseMessage;
import com.ecommerce.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.pojos.CategoryPOJO;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.response.CategoryResponse;
import com.ecommerce.wrapper.CategoryWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        CategoryResponse categoryResponse = new CategoryResponse();
        Sort sortByandSortOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByandSortOrder);
        try {
//            List<Category> categoryList = categoryRepository.findAll();
//            implementing pagination using pageable interface which is compatible with hibernate
            Page<Category> categoryList = categoryRepository.findAll(pageable);
            System.out.println(categoryList);
            if (categoryList.isEmpty()) {
                return categoryResponse;
            }
            categoryResponse.setContent(categoryList.stream().map(CategoryWrapper::new).collect(Collectors.toList()));
            categoryResponse.setPageNumber(categoryList.getNumber());
            categoryResponse.setPageSize(categoryList.getSize());
            categoryResponse.setTotalPages(categoryList.getTotalPages());
            categoryResponse.setTotalSize(categoryList.getTotalElements());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.Category.UNABLE_TO_FETCH);
        }
        return categoryResponse;
    }

    @Override
    public void createCategory(CategoryPOJO categoryPOJO) {
        Category existingCategory = categoryRepository.findByCategoryName(categoryPOJO.getCategoryName());
        if (existingCategory != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.Category.CATEGORY_EXISTS);
        }
        categoryRepository.save(new Category(categoryPOJO.getCategoryName()));
    }

    @Override
    public String removeCategory(long id) {
        if (id == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.Category.MANDATORY_FIELD);
        }
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        categoryRepository.delete(existingCategory);
        return "Successfully removed category with id " + id;

    }


    @Override
    public CategoryResponse updateCategory(long id, CategoryPOJO newCategory) {

        if (id == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ResponseMessage.Category.MANDATORY_FIELD
            );
        }

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.valueOf(id)
                        ));

        existingCategory.setCategoryName(newCategory.getCategoryName());
        return new CategoryResponse(List.of(new CategoryWrapper(categoryRepository.save(existingCategory))));
    }

}
