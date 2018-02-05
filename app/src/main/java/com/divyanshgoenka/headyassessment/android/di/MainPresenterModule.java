package com.divyanshgoenka.headyassessment.android.di;

import com.divyanshgoenka.headyassessment.android.rx.AndroidSchedulersFacade;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.repository.CategoryProductRepository;
import com.divyanshgoenka.headyassessment.rx.SchedulersFacade;
import com.divyanshgoenka.headyassessment.view.MainView;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by divyanshgoenka on 05/02/18.
 */
@Module
public class MainPresenterModule {
    @Provides
    MainPresenter provideMainPresenter(MainView mainView,
                                       CategoryProductRepository categoryProductRepository,
                                       SchedulersFacade schedulersFacade) {
        return new MainPresenter(mainView, categoryProductRepository, schedulersFacade);
    }

    @Provides
    SchedulersFacade providesSchedulersFacade(){
        return new AndroidSchedulersFacade();
    }


}
