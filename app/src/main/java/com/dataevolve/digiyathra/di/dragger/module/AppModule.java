package com.dataevolve.digiyathra.di.dragger.module;


import android.app.Application;
import android.content.Context;

import com.dataevolve.digiyathra.data.local.DigiYathraDataBase;
import com.dataevolve.digiyathra.data.local.dao.LoginDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * The application module which provides app wide instances of various components
 */
@Module
public class AppModule {


    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://www.smscountry.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    DigiYathraDataBase provideDigiYathraDataBase(Application application) {
        return DigiYathraDataBase.getDatabase(application);
    }

    @Provides
    @Singleton
    LoginDao provideLoginDao(DigiYathraDataBase digiYathraDataBase) {
        return digiYathraDataBase.loginDao();
    }

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }


}
