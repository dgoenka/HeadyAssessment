package com.divyanshgoenka.headyassessment.android.di;

import android.content.Context;


import com.divyanshgoenka.headyassessment.android.App;
import com.divyanshgoenka.headyassessment.android.rx.AndroidSchedulersFacade;
import com.divyanshgoenka.headyassessment.repository.CategoryProductRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    CategoryProductRepository provideCategoryProductRepository() {
        return new CategoryProductRepository();
    }

    @Provides
    AndroidSchedulersFacade provideSchedulersFacade() {
        return new AndroidSchedulersFacade();
    }
}
