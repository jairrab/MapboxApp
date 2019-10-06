package com.jairrab.mapboxapp.di.modules

import androidx.lifecycle.ViewModel
import com.jairrab.mapboxapp.di.factory.ViewModelKey
import com.jairrab.presentation.MapControllerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Enable injecting dependencies to ViewModel Constructor
 *
 * @see [https://stackoverflow.com/a/49087002/7960756](https://stackoverflow.com/a/49087002/7960756)
 */
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapControllerViewModel::class)
    abstract fun bindListCharactersViewModel(viewModel: MapControllerViewModel): ViewModel
}
