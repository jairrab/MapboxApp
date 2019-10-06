package com.jairrab.data.store

import javax.inject.Inject

class DataStore @Inject constructor(
    private val cacheDataStore: CacheDataStore,
    private val remoteDataStore: RemoteDataStore
) {

    fun getRemoteData(useRemote: Boolean = false) = when {
        useRemote -> remoteDataStore
        else      -> cacheDataStore
    }
}