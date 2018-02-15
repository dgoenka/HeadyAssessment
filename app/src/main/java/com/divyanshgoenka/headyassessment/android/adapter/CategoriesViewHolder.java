package com.divyanshgoenka.headyassessment.android.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.CategoryList;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by divyanshgoenka on 04/02/18.
 */
public class CategoriesViewHolder extends BaseViewHolder {

    public CategoryList categories;
    @BindView(R.id.grid)
    GridLayout gridLayout;
    LayoutInflater layoutInflater;

    public CategoriesViewHolder(View view, MainPresenter mainPresenter) {
        super(view,mainPresenter);
        ButterKnife.bind(this, view);
        layoutInflater = LayoutInflater.from(view.getContext());
    }

    @Override
    public void bindView(Listable categoryList, String ranking) {
        Logger.d("In getItemViewType, returning ViewTypes.CATEGORIES.viewTypeAndLayoutId ");
        Resources resources = gridLayout.getResources();
        categories = (CategoryList) categoryList;
        gridLayout.getLayoutParams().height = resources.getDimensionPixelSize(R.dimen.category_item_height) * ((int) Math.ceil((double) categories.size() / (double) resources.getInteger(R.integer.category_list_item_column_count)));
        for (Category category : categories) {
            gridLayout.addView(generateCategoryView(category));
        }

    }

    private View generateCategoryView(Category category) {
        View categoryView = layoutInflater.inflate(R.layout.category_list_item, null, true);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder();
        ButterKnife.bind(categoryViewHolder, categoryView);
        //Glide.with(gridLayout.getContext()).load(category.getPicture()).into(categoryViewHolder.categoryImage);
        categoryViewHolder.categoryName.setText(category.getName());
        categoryView.setOnClickListener(v -> {
            mainPresenter.onCategoryClicked(category);
            v.setSelected(true);
        });
        return categoryView;
    }

    public static class CategoryViewHolder {
        @BindView(R.id.category_image)
        ImageView categoryImage;
        @BindView(R.id.category_name)
        TextView categoryName;
    }
}
