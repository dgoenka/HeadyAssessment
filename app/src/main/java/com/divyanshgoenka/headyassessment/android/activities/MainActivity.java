package com.divyanshgoenka.headyassessment.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.android.fragment.KeepClearBackStackListener;
import com.divyanshgoenka.headyassessment.android.fragment.ProductFragment;
import com.divyanshgoenka.headyassessment.android.fragment.RankingFragment;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Category;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.util.Validations;
import com.divyanshgoenka.headyassessment.view.BackButtonConsumer;
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
    private BackButtonConsumer backButtonConsumer;

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
        Logger.d("in switchToRankings, switching to RankingFragment");
        showFragment(RankingFragment.newInstance());
    }

    protected void showFragment(Fragment fragment) {
        resetActionBar();
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int newBackStackLength = fragmentManager.getBackStackEntryCount() + 1;

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, MainActivity.BACK_STACK_ROOT_TAG)
                .commitNow();
    }

    public void resetActionBar() {
        setTitle(R.string.app_name);
        hideBackButton();
        invalidateOptionsMenu();
    }

    @Override
    public MainPresenter getPresenter() {
        return Presenter;
    }

    @Override
    public void showProduct(Product product) {
        Logger.d("in showProduct");
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(ProductActivity.CURRENT_PRODUCT, product);
        startActivity(intent);
    }

    @Override
    public void setTitle(Category category) {
        if (category == null || Validations.isEmptyOrNull(category.getName())) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else {
            getSupportActionBar().setTitle(category.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d("in onOptionsItemSelected, item.getItemId is " + item.getItemId() + " android.R.id.home is " + android.R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (backButtonConsumer != null) {
                    backButtonConsumer.onActivityButonClicked();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setBackButtonConsumer(BackButtonConsumer backButtonConsumer) {
        this.backButtonConsumer = backButtonConsumer;
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new KeepClearBackStackListener(fragmentManager));
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
