package com.jairrab.mapboxapp

import com.jairrab.mapboxapp.di.DaggerAppComponent
import com.jairrab.remote.api.Constants.MAPBOX_API
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        // Mapbox Access token
        Mapbox.getInstance(applicationContext, MAPBOX_API);
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}