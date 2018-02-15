package com.divyanshgoenka.headyassessment.android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.android.adapter.MainPagerAdapter;
import com.divyanshgoenka.headyassessment.android.adapter.ProductRankingRecyclerViewAdapter;
import com.divyanshgoenka.headyassessment.android.animation.FixedSpeedScroller;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.CategoryListableBinding;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.view.MainView;
import com.divyanshgoenka.headyassessment.view.ProductView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductFragment extends Fragment implements ProductView {
    private static final String CATEGORY_ARGUMENT = "CATEGORY_ARGUMENT";
    private static final String CURRENT_POSITION = "CURRENT_POSITION";
    private final List<CategoryListableBinding> categoryListableBindings = new ArrayList<>();
    Category mCategory;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.progress)
    View progress;
    private MainView mainView;
    private MainPresenter mainPresenter;
    private int currentPositon;
    private MainPagerAdapter mainPagerAdapter;
    private LayoutInflater layoutInflater;

    public static ProductFragment newInstance(Category category) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ProductFragment.CATEGORY_ARGUMENT, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = (Category) getArguments().getSerializable(ProductFragment.CATEGORY_ARGUMENT);
            currentPositon = getArguments().getInt(ProductFragment.CURRENT_POSITION);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            mainView = (MainView) context;
            mainPresenter = mainView.getPresenter();
            mainPresenter.setProductView(this);
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainPresenter.setProductView(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ProductFragment.CURRENT_POSITION, currentPositon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_categories_tranverse, container, false);
        ButterKnife.bind(this, view);
        mainPagerAdapter = new MainPagerAdapter();
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPositon = position;
                mainView.setTitle(categoryListableBindings.get(position).getCategory());
            }
        });
        slowDownViewPager();
        return view;
    }

    private void slowDownViewPager() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new DecelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
            // scroller.setFixedDuration(5000);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.loadCategoriesAndProducts(currentPositon, mCategory);
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(int position, List<Listable> listables, Category category) {
        Logger.d("In generateView, listables is " + listables);
        for (int i = position; i < categoryListableBindings.size(); i++) {
            categoryListableBindings.remove(i);
        }
        categoryListableBindings.add(new CategoryListableBinding(category, listables));
        mCategory = category;

        View view = layoutInflater.inflate(R.layout.fragment_product_list, viewPager, false);
        mainPagerAdapter.removeAllStarting(position, viewPager);
        mainPagerAdapter.addView(view);
        mainPagerAdapter.notifyDataSetChanged();

        ListViewHolder listViewHolder = new ListViewHolder();
        ButterKnife.bind(listViewHolder, view);
        listViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listViewHolder.recyclerView.setAdapter(new ProductRankingRecyclerViewAdapter(listables, mainPresenter));
        viewPager.setCurrentItem(position, true);
    }


    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

    }

    @Override
    public void onCategoryClicked(Category category) {
        Logger.d("in ProductFragment.onCategoryClicked");
        mainPresenter.loadCategoriesAndProducts(currentPositon + 1, category);
    }

    public static class ListViewHolder {
        @BindView(R.id.progress)
        View progress;

        @BindView(R.id.list)
        RecyclerView recyclerView;
    }
}
