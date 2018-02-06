package com.divyanshgoenka.headyassessment.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

import butterknife.ButterKnife;

/**
 * Created by divyanshgoenka on 04/02/18.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public final MainPresenter mainPresenter;

    public BaseViewHolder(View itemView, MainPresenter mainPresenter) {
        super(itemView);
        this.mainPresenter = mainPresenter;
        ButterKnife.bind(this, itemView);

    }

    public abstract void bindView(Listable listable, String ranking);
}
