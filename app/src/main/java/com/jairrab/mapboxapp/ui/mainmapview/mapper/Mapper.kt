package com.jairrab.mapboxapp.ui.mainmapview.mapper

import android.location.Location
import com.jairrab.domain.model.MapPoint
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapToLocation(entity: MapPoint): Location {
        return Location("").apply {
            latitude = entity.latitude
            longitude = entity.longitude
        }
    }

    fun mapToLatLng(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    fun mapToLatLng(entity: MapPoint): LatLng {
        return LatLng(entity.latitude, entity.longitude)
    }

    fun mapToPoint(location: Location): Point {
        return Point.fromLngLat(location.longitude, location.latitude)
    }

    fun mapToPoint(entity: MapPoint): Point {
        return Point.fromLngLat(entity.longitude, entity.latitude)
    }

    fun mapToFeatureCollections(points: List<MapPoint>): FeatureCollection {
        return FeatureCollection.fromFeatures(points.map { getFeature(it) })
    }

    private fun getFeature(mapPoint: MapPoint): Feature {
        val point = Point.fromLngLat(mapPoint.longitude, mapPoint.latitude)
        val feature = Feature.fromGeometry(point)
        feature.addStringProperty("name", mapPoint.name)
        feature.addStringProperty("description", mapPoint.description)
        feature.addNumberProperty("id", mapPoint.id)
        return feature
    }
}