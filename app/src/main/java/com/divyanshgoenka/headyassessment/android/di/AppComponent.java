package com.divyanshgoenka.headyassessment.android.di;


import com.divyanshgoenka.headyassessment.android.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class})
public interface AppComponent {
    void inject(App app);
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);
        AppComponent build();
    }
}
