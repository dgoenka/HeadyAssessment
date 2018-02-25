package com.divyanshgoenka.headyassessment.view;

import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Listable;

import java.util.ArrayList;

/**
 * Created by divyanshgoenka on 14/02/18.
 */

public interface ProductView extends BaseView {
    void showLoading();

    void showList(int position, ArrayList<Listable> listables, Category category);

    void hideLoading();

    void showError();

    void onCategoryClicked(Category category);
}
