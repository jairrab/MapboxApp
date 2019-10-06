package com.jairrab.mapboxapp

import com.jairrab.mapboxapp.di.DaggerAppComponent
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        // Mapbox Access token
        Mapbox.getInstance(
            applicationContext,
            getString(R.string.mapbox_access_token)
        );
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}