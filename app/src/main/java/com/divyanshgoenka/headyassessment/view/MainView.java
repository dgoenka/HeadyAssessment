package com.divyanshgoenka.headyassessment.view;

import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

/**
 * Created by divyanshgoenka on 03/02/18.
 */

public interface MainView extends BaseView {
    void switchToProducts();

    void switchToRankings();

    MainPresenter getPresenter();

    void showProduct(Product product);

    void setTitle(Category category);

    void hideBackButton();

    void showBackButton();

    void setBackButtonConsumer(BackButtonConsumer backButtonConsumer);
}
