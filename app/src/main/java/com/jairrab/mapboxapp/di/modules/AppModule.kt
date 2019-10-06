package com.jairrab.mapboxapp.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jairrab.mapboxapp.ui.utils.Toaster
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @JvmStatic
    @Provides
    fun providesDefaultSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @JvmStatic
    @Provides
    fun providesToaster(application: Application): Toaster {
        return Toaster(application)
    }
}
