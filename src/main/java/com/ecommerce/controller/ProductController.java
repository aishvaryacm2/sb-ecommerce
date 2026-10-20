package com.ecommerce.controller;

import com.ecommerce.constants.AppConstants;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.response.ProductResponse;
import com.ecommerce.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(path = "/admin/categories/{categoryId}/product", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                        @PathVariable Long categoryId) {
        try {
            ProductResponse body = productService.addProduct(productDTO, categoryId);
            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    @RequestMapping(path = "/public/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder) {
        try {
            ProductResponse body = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    @RequestMapping(path = "/public/categories/{categoryId}/products", method = RequestMethod.GET)
    public ResponseEntity<?> getProductsByCateogoryId(@PathVariable Long categoryId,
                                                      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                      @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder) {
        try {
            ProductResponse body = productService.searchByCategoryId(categoryId,pageNumber,pageSize,sortBy,sortOrder);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    @RequestMapping(path = "/public/products/keyword/{keyword}")
    public ResponseEntity<?> getProductsByKeyword(@PathVariable String keyword,
                                                  @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                  @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                  @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder) {
        try {
            ProductResponse body = productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    @RequestMapping(path = "/admin/products/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductResponse body = productService.updateProductById(productId, productDTO);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }
    }

    @RequestMapping(path = "/admin/products/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            ProductResponse body = productService.removeProduct(productId);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (ResponseStatusException responseStatusException) {
            return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getReason());
        }

    }

    @RequestMapping(path = "/products/{productId}/image", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile file) {
        try {
            ProductResponse body = productService.updateProductImage(productId, file);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) {
                return ResponseEntity.status(((ResponseStatusException) e).getStatusCode()).body(((ResponseStatusException) e).getBody());
            } else if (e instanceof IOException) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
