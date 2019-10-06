package com.jairrab.mapboxapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.jairrab.mapboxapp.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Enable injecting dependencies to ViewModel Constructor
 *
 * @see [https://stackoverflow.com/a/49087002/7960756](https://stackoverflow.com/a/49087002/7960756)
 */
@Module
internal abstract class ViewModelFactoryModule {

    //see https://medium.com/chili-labs/android-viewmodel-injection-with-dagger-f0061d3402ff
    //for reason behind Singleton scoping
    @Singleton
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
