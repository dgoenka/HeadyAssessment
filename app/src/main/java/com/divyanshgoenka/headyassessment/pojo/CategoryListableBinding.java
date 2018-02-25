package com.divyanshgoenka.headyassessment.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by divyanshgoenka on 15/02/18.
 */

public class CategoryListableBinding implements Serializable {
    Category category;
    ArrayList<Listable> listableList;

    public CategoryListableBinding(Category category, ArrayList<Listable> listableList) {
        this.category = category;
        this.listableList = listableList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Listable> getListableList() {
        return listableList;
    }

    public void setListableList(ArrayList<Listable> listableList) {
        this.listableList = listableList;
    }

}
