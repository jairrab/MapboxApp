package com.jairrab.data.repository

import com.jairrab.data.model.MapPointData
import io.reactivex.Completable
import io.reactivex.Observable
import java.sql.Timestamp

interface DataRepository {
    fun getMapPoints(): Observable<List<MapPointData>>

    fun saveMapPoints(list: List<MapPointData>):Completable

    fun updateLastLocationTimeStamp(timestamp: Long):Completable

    fun getLastLocationTimeStamp():Observable<Long>
}