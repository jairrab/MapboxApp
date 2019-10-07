package com.jairrab.remote.service

import com.jairrab.data.model.MapPointData
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitTestClient {

    @GET("get_map_pins.php")
    fun getMapPoints(): Observable<List<MapPointData>>
}