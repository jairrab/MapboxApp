package com.jairrab.cache

import com.jairrab.cache.db.AppDatabase
import com.jairrab.cache.db.constants.MapPointTable
import com.jairrab.cache.entities.DataProperty
import com.jairrab.cache.mapper.MapPointMapper
import com.jairrab.data.model.MapPointData
import com.jairrab.data.repository.CacheRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val database: AppDatabase,
    private val mapPointMapper: MapPointMapper

) : CacheRepository {

    override fun getMapPoints(): Observable<List<MapPointData>> {
        return database.cachedDao().getMapPoints()
            .flatMap { list ->
                Observable.just(list.map { mapPointMapper.mapToData(it) })
            }
    }

    override fun saveMapPoints(list: List<MapPointData>): Completable {
        return database.cachedDao()
            .saveMapPoints(list.map { mapPointMapper.mapToEntity(it) })
    }

    override fun updateLastLocationTimeStamp(timestamp: Long): Completable {
        return database.cachedDao().updateProperty(
            DataProperty(
                id = MapPointTable.DATA_LAST_LOCATION_TIMESTAMP_ID,
                property = timestamp.toString()
            )
        )
    }

    override fun getLastLocationTimeStamp(): Observable<Long> {
        return database.cachedDao()
            .getProperty(MapPointTable.DATA_LAST_LOCATION_TIMESTAMP_ID)
            .map { it.toLong() }
    }
}