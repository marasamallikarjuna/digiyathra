package com.dataevolve.digiyathra.di.dragger.login;

import androidx.lifecycle.ViewModel;

import com.dataevolve.digiyathra.di.dragger.module.ViewModelKey;
import com.dataevolve.digiyathra.view.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);
}