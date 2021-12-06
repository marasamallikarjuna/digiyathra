package com.dataevolve.digiyathra.di.dragger.components;

import android.app.Application;

import com.dataevolve.digiyathra.DigiYathraApp;
import com.dataevolve.digiyathra.di.dragger.builder.ActivityBuilderModule;
import com.dataevolve.digiyathra.di.dragger.module.AppModule;
import com.dataevolve.digiyathra.di.dragger.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<DigiYathraApp> {


    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}





