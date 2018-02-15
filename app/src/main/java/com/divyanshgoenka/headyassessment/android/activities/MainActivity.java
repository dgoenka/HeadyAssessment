package com.divyanshgoenka.headyassessment.android.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.android.fragment.ProductFragment;
import com.divyanshgoenka.headyassessment.android.fragment.RankingFragment;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.util.Validations;
import com.divyanshgoenka.headyassessment.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAcitivity<MainPresenter> implements MainView {

    private static final int DEFAULT_BOTTOM_NAVIGATION_POSITION = R.id.navigation_products;
    private static final String TAG = "MainActivity";
    private static final String BACK_STACK_ROOT_TAG = "BACK_STACK_ROOT_TAG";
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_products:
                    Presenter.switchToProducts();
                    return true;
                case R.id.navigation_rankings:
                    Presenter.switchToRankings();
                    return true;

            }
            return false;
        }

    };
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_container)
    FrameLayout container;

    @Override
    public void switchToProducts() {
        Logger.d("in switchToProducts, switching to ProductFragment");
        showFragment(ProductFragment.newInstance(null));
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void switchToRankings() {
        showFragment(RankingFragment.newInstance());
    }

    protected void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commitNowAllowingStateLoss();
        invalidateOptionsMenu();
        setTitle(R.string.app_name);
    }

    @Override
    public MainPresenter getPresenter() {
        return Presenter;
    }

    @Override
    public void showProduct(Product product) {

    }

    @Override
    public void setTitle(Category category) {
        if (category == null || Validations.isEmptyOrNull(category.getName())) {
            setTitle(R.string.app_name);
        } else {
            super.setTitle(category.getName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState==null){
            mBottomNavigationView.setSelectedItemId(MainActivity.DEFAULT_BOTTOM_NAVIGATION_POSITION);
        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }



    @Override
    void setPresenter() {
        Presenter.set(this);
    }



    @Override
    protected void unsetPresenter() {
        Presenter.set(null);
    }
}
