package com.jairrab.remote

import com.jairrab.data.model.MapPointData
import com.jairrab.data.repository.RemoteRepository
import com.jairrab.remote.service.RetrofitService
import io.reactivex.Observable
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val retrofitService: RetrofitService
) : RemoteRepository {

    override fun getMapPoints(): Observable<List<MapPointData>> {
        return retrofitService.getMapPoints()
    }
}