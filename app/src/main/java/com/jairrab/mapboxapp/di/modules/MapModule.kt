package com.jairrab.mapboxapp.di.modules

import android.app.Application
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import dagger.Module
import dagger.Provides

@Module
object MapModule {

    @JvmStatic
    @Provides
    fun providesLocationEngine(application: Application): LocationEngine {
        return LocationEngineProvider.getBestLocationEngine(application)
    }
}
