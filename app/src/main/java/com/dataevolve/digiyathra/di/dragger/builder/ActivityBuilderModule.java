package com.dataevolve.digiyathra.di.dragger.builder;



import com.dataevolve.digiyathra.di.dragger.login.LoginModule;
import com.dataevolve.digiyathra.di.dragger.login.LoginScope;
import com.dataevolve.digiyathra.di.dragger.login.LoginViewModelModule;
import com.dataevolve.digiyathra.di.dragger.signup.SignUpModule;
import com.dataevolve.digiyathra.di.dragger.signup.SignUpScope;
import com.dataevolve.digiyathra.di.dragger.signup.SignUpViewModelModule;
import com.dataevolve.digiyathra.view.login.LoginActivity;
import com.dataevolve.digiyathra.view.signup.SignUpActivity;
import com.dataevolve.digiyathra.view.submit.SubmitActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * The module which provides the android injection service to Activities.
 */
@Module
public abstract class ActivityBuilderModule {

    @LoginScope
    @ContributesAndroidInjector( modules = {LoginViewModelModule.class, LoginModule.class})
    abstract LoginActivity contributeLoginActivity();

    @SignUpScope
    @ContributesAndroidInjector( modules = {SignUpViewModelModule.class, SignUpModule.class})
    abstract SignUpActivity contributeSignUpActivity();

    @ContributesAndroidInjector()
    abstract SubmitActivity contributeSubmitActivity();



}
