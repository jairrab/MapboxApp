package com.jairrab.mapboxapp.ui.mainmapview.helpers.distance

import android.location.Location
import com.jairrab.presentation.utils.roundToNDecimal
import com.mapbox.geojson.Point
import javax.inject.Inject

internal class DistanceUtils @Inject constructor(
    private val utils: MeasurementUtils
) {
    fun getDistanceMilesAndBearing(point1: Point, point2: Point): String {
        val miles = getProperDistanceMiles(point1, point2)
        val bearing = getBearingDegrees(point1, point2)
        return "${miles}miles : $bearing"
    }

    fun getDistanceMilesAndBearing(
        latitude: Double,
        longitude: Double,
        currentLocation: Location
    ): String {
        val point1 = Point.fromLngLat(longitude, latitude)
        val point2 = Point.fromLngLat(currentLocation.longitude, currentLocation.latitude)
        return getDistanceMilesAndBearing(point1, point2)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getProperDistanceMiles(point1: Point, point2: Point): String {
        val kilometers = utils.distance(point1, point2)
        val miles = kilometers * 0.621371
        return miles.roundToNDecimal(2).toString()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getBearingDegrees(point1: Point, point2: Point): String {
        val degrees = utils.bearing(point1, point2).roundToNDecimal(2)
        return "$degrees°"
    }
}