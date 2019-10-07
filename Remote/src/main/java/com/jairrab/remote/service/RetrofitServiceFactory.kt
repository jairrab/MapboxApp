package com.jairrab.remote.service

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitServiceFactory {

    fun makeRetrofitTestClient(isDebug: Boolean): RetrofitTestClient {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeRetrofitTestClient(okHttpClient, Gson())
    }

    fun makeRetrofitMapbox(isDebug: Boolean): RetrofitMapbox {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeRetrofitMapbox(okHttpClient, Gson())
    }

    private fun makeRetrofitTestClient(okHttpClient: OkHttpClient, gson: Gson): RetrofitTestClient {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://annetog.go" + "ten" + "na.com/development/scripts/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(RetrofitTestClient::class.java)
    }

    private fun makeRetrofitMapbox(okHttpClient: OkHttpClient, gson: Gson): RetrofitMapbox {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/geocoding/v5/")
            .client(okHttpClient)

            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(RetrofitMapbox::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

}