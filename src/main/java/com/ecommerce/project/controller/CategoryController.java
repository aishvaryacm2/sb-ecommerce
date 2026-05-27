package com.ecommerce.project.controller;

import com.ecommerce.constants.ResponseMessage;
import com.ecommerce.model.Category;
import com.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    //@GetMapping("/api/public/categories")
    @RequestMapping(path = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> body = categoryService.getCategories();
            return body.isEmpty() ? ResponseEntity.status(HttpStatusCode.valueOf(204)).build() : ResponseEntity.ok(body);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    //    @PostMapping("/api/public/categories")
    @RequestMapping(path = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategories(@RequestBody Category category) {
        try {
            categoryService.createCategory(category);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return ResponseEntity.ok(ResponseMessage.Category.SUCCESSFULLY_ADDED);
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
public ResponseEntity<?> updateCategories(@RequestBody Category category,
                                              @PathVariable long categoryId) {
        try {
            Category body = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok(ResponseMessage.Category.SUCCESSFULLY_UPDATED + ": " + body.toString());

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
