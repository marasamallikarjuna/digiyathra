package com.dataevolve.digiyathra.di.dragger.module;

import androidx.lifecycle.ViewModelProvider;

import com.dataevolve.digiyathra.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
