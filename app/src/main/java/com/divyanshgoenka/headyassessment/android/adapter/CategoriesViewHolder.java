package com.divyanshgoenka.headyassessment.android.adapter;

import android.view.View;

import com.divyanshgoenka.headyassessment.pojo.CategoryList;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

/**
 * Created by divyanshgoenka on 04/02/18.
 */
public class CategoriesViewHolder extends BaseViewHolder {

    public CategoryList mItem;

    public CategoriesViewHolder(View view, MainPresenter mainPresenter) {
        super(view,mainPresenter);
    }

    @Override
    public void bindView(Listable categoryList) {
        mItem = (CategoryList) categoryList;
        //itemView.setOnClickListener(v-> mainPresenter.onProductClicked(mItem));
    }
}
