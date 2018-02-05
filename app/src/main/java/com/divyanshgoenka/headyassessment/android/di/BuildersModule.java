package com.divyanshgoenka.headyassessment.android.di;

import com.divyanshgoenka.headyassessment.android.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {MainViewModule.class, MainPresenterModule.class})
    abstract MainActivity bindMainActivity();

}
