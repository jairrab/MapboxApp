package com.jairrab.mapboxapp.di.modules

import android.app.Application
import com.jairrab.cache.RoomRepository
import com.jairrab.cache.db.AppDatabase
import com.jairrab.data.repository.CacheRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindCacheRepository(roomRepository: RoomRepository): CacheRepository
}