package com.jairrab.mapboxapp.di.modules

import com.jairrab.data.repository.RemoteRepository
import com.jairrab.mapboxapp.BuildConfig
import com.jairrab.remote.RetrofitClient
import com.jairrab.remote.service.RetrofitService
import com.jairrab.remote.service.RetrofitServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofitService(): RetrofitService {
            return RetrofitServiceFactory.makeRetrofitClient(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(retrofitClient: RetrofitClient): RemoteRepository
}