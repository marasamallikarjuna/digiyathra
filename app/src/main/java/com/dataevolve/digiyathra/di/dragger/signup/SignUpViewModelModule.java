package com.dataevolve.digiyathra.di.dragger.signup;

import androidx.lifecycle.ViewModel;

import com.dataevolve.digiyathra.di.dragger.module.ViewModelKey;
import com.dataevolve.digiyathra.view.signup.SignUpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SignUpViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindSignUpViewModel(SignUpViewModel loginViewModel);
}