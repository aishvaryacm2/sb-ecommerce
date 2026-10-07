package com.ecommerce.response;

import com.ecommerce.pojos.CategoryPOJO;

import java.util.List;
import java.util.stream.Stream;

public class CategoryResponse {

    private List<CategoryPOJO> content;
    private int pageNumber;
    private int pageSize;
    private long totalSize;
    private int totalPages;


    public CategoryResponse() {
    }

    public CategoryResponse(List<CategoryPOJO> content) {
        this.content = content;
    }

    public List<CategoryPOJO> getContent() {
        return content;
    }

    public void setContent(List<CategoryPOJO> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "content=" + content.stream().map(Object::toString).toList() +
                '}';
    }
}
