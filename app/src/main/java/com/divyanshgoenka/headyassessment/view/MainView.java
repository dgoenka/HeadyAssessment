package com.divyanshgoenka.headyassessment.view;

import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

/**
 * Created by divyanshgoenka on 03/02/18.
 */

public interface MainView extends BaseView {
    void switchToProducts();

    void switchToRankings();

    MainPresenter getPresenter();
}
