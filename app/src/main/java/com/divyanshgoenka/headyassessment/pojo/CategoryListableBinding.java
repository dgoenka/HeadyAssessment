package com.divyanshgoenka.headyassessment.pojo;

import java.util.List;

/**
 * Created by divyanshgoenka on 15/02/18.
 */

public class CategoryListableBinding {
    Category category;
    List<Listable> listableList;

    public CategoryListableBinding(Category category, List<Listable> listableList) {
        this.category = category;
        this.listableList = listableList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Listable> getListableList() {
        return listableList;
    }

    public void setListableList(List<Listable> listableList) {
        this.listableList = listableList;
    }

}
