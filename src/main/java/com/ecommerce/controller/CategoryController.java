package com.ecommerce.controller;

import com.ecommerce.constants.AppConstants;
import com.ecommerce.constants.ResponseMessage;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.response.CategoryResponse;
import com.ecommerce.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    //@GetMapping("/api/public/categories")
    @RequestMapping(path = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                           @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                           @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
                                           @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder) {
        try {
            CategoryResponse body = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);
            return ResponseEntity.ok(body);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    //    @PostMapping("/api/public/categories")
    @RequestMapping(path = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategories(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.createCategory(categoryDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return ResponseEntity.ok(ResponseMessage.Category.SUCCESSFULLY_ADDED + " " + categoryDTO.getCategoryName());
    }

    //    @DeleteMapping("/api/admin/categories/{categoryId}")
    @RequestMapping(path = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCategory(@PathVariable long categoryId) {
        try {
            String message = categoryService.removeCategory(categoryId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }

    }

    //    @PutMapping("/api/public/categories/{categoryId}")
    @RequestMapping(path = "/public/categories/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategories(@RequestBody CategoryDTO categoryDTO,
                                              @PathVariable long categoryId) {
        try {
            CategoryResponse body = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.ok(ResponseMessage.Category.SUCCESSFULLY_UPDATED + ": " + body.toString());

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
