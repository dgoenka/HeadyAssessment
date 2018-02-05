package com.divyanshgoenka.headyassessment.android.di;

import com.divyanshgoenka.headyassessment.android.activities.MainActivity;
import com.divyanshgoenka.headyassessment.android.rx.AndroidSchedulersFacade;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.repository.CategoryProductRepository;
import com.divyanshgoenka.headyassessment.view.MainView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainViewModule {

    @Binds
    abstract MainView provideMainView(MainActivity mainActivity);


}
