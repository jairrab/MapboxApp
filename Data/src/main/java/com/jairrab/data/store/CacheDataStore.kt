package com.jairrab.data.store

import com.jairrab.data.model.MapPointData
import com.jairrab.data.repository.CacheRepository
import com.jairrab.data.repository.DataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CacheDataStore @Inject constructor(
    private val cacheRepository: CacheRepository
) : DataRepository {

    override fun getMapPoints(): Observable<List<MapPointData>> {
        return cacheRepository.getMapPoints()
    }

    override fun saveMapPoints(list: List<MapPointData>): Completable {
        return cacheRepository.saveMapPoints(list)
    }

    override fun updateLastLocationTimeStamp(timestamp: Long): Completable {
        return cacheRepository.updateLastLocationTimeStamp(timestamp)
    }

    override fun getLastLocationTimeStamp(): Observable<Long> {
        return cacheRepository.getLastLocationTimeStamp()
    }
}