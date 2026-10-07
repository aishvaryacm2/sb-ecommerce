package com.ecommerce.wrapper;

import com.ecommerce.model.Category;
import com.ecommerce.pojos.CategoryPOJO;

public class CategoryWrapper extends CategoryPOJO {

    private Category category;

    public CategoryWrapper(Category category) {
       this.category = category;
    }

    @Override
    public long getId() {
       return category.getId();
    }

    @Override
    public String getCategoryName(){
        return category.getCategoryName();
    }

    @Override
    public String toString() {
        return "CategoryPOJO{" +
                "id=" + getId() +
                ", categoryName='" + getCategoryName() + '\'' +
                '}';
    }
}
