package com.divyanshgoenka.headyassessment.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by divyanshgoenka on 04/02/18.
 */
public class ProductViewHolder extends BaseViewHolder {

    @BindView(R.id.imageView)
    ImageView productImageView;
    @BindView(R.id.name)
    TextView productName;
    @BindView(R.id.content)
    TextView productContent;

    public Product mItem;

    public ProductViewHolder(View view, MainPresenter mainPresenter) {
        super(view,mainPresenter);
        ButterKnife.bind(this,view);
    }

    @Override
    public void bindView(Listable product) {
        mItem = (Product) product;
        itemView.setOnClickListener(v-> mainPresenter.onProductClicked(mItem));
        //Glide.with(productImageView).load(mItem.picture).into(productImageView);
        productName.setText(mItem.getName());
        productContent.setText(mItem.getViewCount().toString());

    }
}