package com.divyanshgoenka.headyassessment.android.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.android.fragment.RankingFragment;
import com.divyanshgoenka.headyassessment.view.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

public class MainActivity extends BaseAcitivity implements MainView {

    private static final int DEFAULT_BOTTOM_NAVIGATION_POSITION = R.id.navigation_rankings;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.fragment_container)
    FrameLayout container;

    @Override
    public void switchToProducts() {

    }


    @Override
    public void switchToRankings() {
        showFragment(RankingFragment.newInstance());
    }

    @Override
    public MainPresenter getPresenter() {
        return mainPresenter;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener () {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_products:
                    mainPresenter.switchToProducts();
                    return true;
                case R.id.navigation_rankings:
                    mainPresenter.switchToRankings();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidInjection.inject(this);
        ButterKnife.bind(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState==null){
            mBottomNavigationView.setSelectedItemId(DEFAULT_BOTTOM_NAVIGATION_POSITION);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }



}
