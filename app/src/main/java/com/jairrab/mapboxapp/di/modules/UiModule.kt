package com.jairrab.mapboxapp.di.modules

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.mapboxapp.di.scope.ActivityScope
import com.jairrab.mapboxapp.scheduler.UiThread
import com.jairrab.mapboxapp.ui.mainactivity.MainActivity
import com.jairrab.mapboxapp.ui.mainmapview.MainMapView
import com.jairrab.presentation.MapControllerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        ViewModelModule::class
    ]
)
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            InjectViewModel::class
        ]
    )
    abstract fun contributeMainMapView(): MainMapView

    //see https://medium.com/chili-labs/android-viewmodel-injection-with-dagger-f0061d3402ff
    //for more info
    @Module
    class InjectViewModel {
        @Provides
        fun mapControllerViewModel(
            factory: ViewModelProvider.Factory,
            target: MainMapView
        ) = ViewModelProviders.of(target, factory).get(MapControllerViewModel::class.java)

    }
}