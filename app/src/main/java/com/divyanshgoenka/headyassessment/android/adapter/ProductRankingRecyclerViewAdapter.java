package com.divyanshgoenka.headyassessment.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

import java.util.List;

import javax.annotation.Nullable;


public class ProductRankingRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<? extends Listable> mValues;
    private final MainPresenter mainPresenter;
    private String ranking;

    public ProductRankingRecyclerViewAdapter(List<? extends Listable> mValues, MainPresenter mainPresenter) {
        this.mValues = mValues;
        this.mainPresenter = mainPresenter;
    }

    public ProductRankingRecyclerViewAdapter(List<? extends Listable> items, MainPresenter mainPresenter, @Nullable String ranking) {
        this(items, mainPresenter);
        this.ranking = ranking;
    }


    @Override
    public int getItemViewType(int position) {
        Logger.d("In getItemViewType, position is " + position);
        if (mValues.get(position) instanceof Product) {
            return ViewTypes.PRODUCT.viewTypeAndLayoutId;
        } else {
            Logger.d("In getItemViewType, returning ViewTypes.CATEGORIES.viewTypeAndLayoutId ");
            return ViewTypes.CATEGORIES.viewTypeAndLayoutId;
        }

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.d("In onCreateViewHolder, viewType is " + viewType);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        Logger.d("In onCreateViewHolder, view is " + view);

        if (viewType == ViewTypes.CATEGORIES.viewTypeAndLayoutId) {
            Logger.d("returning CategoriesViewHolder");

            return new CategoriesViewHolder(view, mainPresenter);
        }

        return new ProductViewHolder(view, mainPresenter);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.bindView(mValues.get(position), ranking);

    }

    @Override
    public int getItemCount() {
        Logger.d("in getItemCount, item count is" + mValues.size());
        return mValues.size();
    }

    public enum ViewTypes{
        PRODUCT(R.layout.product_list_item), CATEGORIES(R.layout.categories_list_item);

        int viewTypeAndLayoutId;

        ViewTypes(int viewType) {
            viewTypeAndLayoutId = viewType;
        }
    }

}
