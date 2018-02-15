package com.divyanshgoenka.headyassessment.view;

import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Listable;

import java.util.List;

/**
 * Created by divyanshgoenka on 14/02/18.
 */

public interface ProductView extends BaseView {
    void showLoading();

    void showList(int position, List<Listable> listables);

    void hideLoading();

    void showError();

    void onCategoryClicked(Category category);
}
