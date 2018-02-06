package com.divyanshgoenka.headyassessment.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

import java.util.List;

import javax.annotation.Nullable;


public class ProductRankingRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<? extends Listable> mValues;
    private MainPresenter mainPresenter;
    private String ranking;

    public ProductRankingRecyclerViewAdapter(List<? extends Listable> items, MainPresenter mainPresenter,@Nullable String ranking) {
        this.mValues = items;
        this.mainPresenter = mainPresenter;
        this.ranking = ranking;
    }



    @Override
    public int getItemViewType(int position) {
        if(mValues.get(position) instanceof Product)
            return ViewTypes.PRODUCT.viewTypeAndLayoutId;
        else
            return ViewTypes.CATEGORIES.viewTypeAndLayoutId;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return new ProductViewHolder(view, mainPresenter);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {

        holder.bindView(mValues.get(position), ranking);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public enum ViewTypes{
        PRODUCT(R.layout.fragment_product), CATEGORIES(R.layout.fragment_product);

        int viewTypeAndLayoutId;

        ViewTypes(int viewType) {
            this.viewTypeAndLayoutId = viewType;
        }
    }

}
