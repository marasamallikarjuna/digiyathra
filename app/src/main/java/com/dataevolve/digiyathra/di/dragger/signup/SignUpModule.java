package com.dataevolve.digiyathra.di.dragger.signup;


import com.dataevolve.digiyathra.data.api.LoginApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class SignUpModule {

    @SignUpScope
    @Provides
    static LoginApi provideLoginApi(Retrofit retrofit){
        return retrofit.create(LoginApi.class);
    }
}
