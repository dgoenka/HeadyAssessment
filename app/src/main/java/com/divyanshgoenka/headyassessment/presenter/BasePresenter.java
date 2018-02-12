package com.divyanshgoenka.headyassessment.presenter;

import com.divyanshgoenka.headyassessment.view.BaseView;

/**
 * Created by divyanshgoenka on 06/02/18.
 */

public interface BasePresenter<V extends BaseView> {
    public void set(V view);
}
