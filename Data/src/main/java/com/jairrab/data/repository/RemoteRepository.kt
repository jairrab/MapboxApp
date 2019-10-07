package com.jairrab.data.repository

import com.jairrab.data.model.LocationQuery
import com.jairrab.data.model.MapPointData
import io.reactivex.Observable

interface RemoteRepository {
    fun getMapPoints(): Observable<List<MapPointData>>

    fun getLocationQuery(latitude: Double, longitude: Double): Observable<LocationQuery>
}