package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.response.ProductResponse;
import com.ecommerce.wrapper.ProductWrapper;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, FileService fileService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    @Override
    public ProductResponse addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for categoryID : " + categoryId));
        boolean ifProductNotPresent = true;
        List<Product> productList = category.getProductList();
        for (Product product : productList) {
            if (product.getProductName().equals(productDTO.getProductName())) {
                ifProductNotPresent = false;
                break;
            }
        }
        if (ifProductNotPresent) {
            Product product = new Product();
            product.setCategory(category);
            product.setImage("http random");
            double specialPrice = productDTO.getPrice() - (productDTO.getDiscount() * 0.01) * productDTO.getPrice();
            product.setPrice(productDTO.getPrice());
            product.setDiscount(productDTO.getDiscount());
            product.setSpecialPrice(specialPrice);
            product.setDescription(productDTO.getDescription());
            product.setProductName(productDTO.getProductName());
            product.setQuantity(productDTO.getQuantity());
            Product result = productRepository.save(product);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(List.of(new ProductWrapper(result)));
            return productResponse;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable = getPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(new ProductWrapper(product));
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalSize(productPage.getTotalElements());
        return productResponse;
    }

    private Pageable getPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    }

    @Override
    public ProductResponse searchByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for categoryID : " + categoryId));
        Pageable pageable = getPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageable);
        List<ProductDTO> productDTOs = new ArrayList<>();
        productPage.forEach(product -> productDTOs.add(new ProductWrapper(product)));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalSize(productPage.getTotalElements());
        return productResponse;
    }

    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable = getPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageable);
        List<ProductDTO> productDTOs = new ArrayList<>();
        productPage.forEach(product -> productDTOs.add(new ProductWrapper(product)));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalSize(productPage.getTotalElements());
        return productResponse;
    }

    @Override
    public ProductResponse updateProductById(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for productId : " + productId));
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDiscount(productDTO.getDiscount());
        existingProduct.setSpecialPrice(productDTO.getPrice() - (productDTO.getDiscount() * 0.01) * productDTO.getPrice());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(List.of(new ProductWrapper(existingProduct)));
        return productResponse;
    }

    @Override
    public ProductResponse removeProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for productId : " + productId));
        productRepository.delete(existingProduct);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(List.of(new ProductWrapper(existingProduct)));
        return productResponse;
    }

    @Override
    public ProductResponse updateProductImage(Long productId, MultipartFile file) throws IOException {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for productId : " + productId));
        String fileName = fileService.uploadImage(path, file);
        existingProduct.setImage(fileName);
        productRepository.save(existingProduct);
        ProductResponse productResponse= new ProductResponse();
        productResponse.setContent(List.of(new ProductWrapper(existingProduct)));
        return productResponse;
    }


}
