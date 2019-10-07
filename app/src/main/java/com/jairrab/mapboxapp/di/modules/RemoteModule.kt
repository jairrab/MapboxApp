package com.jairrab.mapboxapp.di.modules

import com.jairrab.data.repository.RemoteRepository
import com.jairrab.mapboxapp.BuildConfig
import com.jairrab.remote.RetrofitClient
import com.jairrab.remote.service.RetrofitMapbox
import com.jairrab.remote.service.RetrofitServiceFactory
import com.jairrab.remote.service.RetrofitTestClient
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofitTestClient(): RetrofitTestClient {
            return RetrofitServiceFactory.makeRetrofitTestClient(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideRetrofitMapbox(): RetrofitMapbox {
            return RetrofitServiceFactory.makeRetrofitMapbox(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(retrofitClient: RetrofitClient): RemoteRepository
}