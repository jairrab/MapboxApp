package com.jairrab.data.store

import com.jairrab.data.model.MapPointData
import com.jairrab.data.repository.DataRepository
import com.jairrab.data.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataStore @Inject constructor(
    private val remoteRepository: RemoteRepository
) : DataRepository {

    override fun getMapPoints(): Observable<List<MapPointData>> {
        return remoteRepository.getMapPoints()
    }

    override fun saveMapPoints(list: List<MapPointData>): Completable {
        throw IllegalStateException("Function not currently supported!")
    }

    override fun updateLastLocationTimeStamp(timestamp: Long): Completable {
        throw IllegalStateException("Function not currently supported!")
    }

    override fun getLastLocationTimeStamp(): Observable<Long> {
        throw IllegalStateException("Function not currently supported!")
    }
}