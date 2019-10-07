package com.jairrab.remote.service

import com.jairrab.data.model.LocationQuery
import com.jairrab.remote.api.Constants.MAPBOX_API
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMapbox {

    @GET("mapbox.places/{longitude}%2C%20{latitude}.json")
    fun getLocationQuery(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("access_token") query: String = MAPBOX_API
    ): Observable<LocationQuery>
}