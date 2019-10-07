package com.jairrab.remote

import com.jairrab.data.model.LocationQuery
import com.jairrab.data.model.MapPointData
import com.jairrab.data.repository.RemoteRepository
import com.jairrab.remote.service.RetrofitMapbox
import com.jairrab.remote.service.RetrofitTestClient
import io.reactivex.Observable
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val retrofitTestClient: RetrofitTestClient,
    private val retrofitMapbox: RetrofitMapbox
) : RemoteRepository {

    override fun getMapPoints(): Observable<List<MapPointData>> {
        return retrofitTestClient.getMapPoints()
    }

    override fun getLocationQuery(latitude: Double, longitude: Double): Observable<LocationQuery> {
        return retrofitMapbox.getLocationQuery(latitude,longitude)
    }
}