
package com.divyanshgoenka.headyassessment.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Category implements Serializable {

    @SerializedName("child_categories")
    private List<Object> mChildCategories;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("products")
    private List<Product> mProducts;

    public List<Object> getChildCategories() {
        return mChildCategories;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public static class Builder {

        private List<Object> mChildCategories;
        private Long mId;
        private String mName;
        private List<Product> mProducts;

        public Category.Builder withChildCategories(List<Object> childCategories) {
            mChildCategories = childCategories;
            return this;
        }

        public Category.Builder withId(Long id) {
            mId = id;
            return this;
        }

        public Category.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Category.Builder withProducts(List<Product> products) {
            mProducts = products;
            return this;
        }

        public Category build() {
            Category Category = new Category();
            Category.mChildCategories = mChildCategories;
            Category.mId = mId;
            Category.mName = mName;
            Category.mProducts = mProducts;
            return Category;
        }

    }

}
