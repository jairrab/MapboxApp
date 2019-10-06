package com.jairrab.cache.mapper

import com.jairrab.cache.entities.MapPointEntity
import com.jairrab.data.model.MapPointData
import javax.inject.Inject

class MapPointMapper @Inject constructor(

) {
    fun mapToData(it: MapPointEntity): MapPointData {
        return MapPointData(
            id = it.id,
            description = it.description,
            latitude = it.latitude,
            longitude = it.longitude,
            name = it.name
        )
    }

    fun mapToEntity(it: MapPointData): MapPointEntity {
        return MapPointEntity(
            id = it.id,
            description = it.description,
            latitude = it.latitude,
            longitude = it.longitude,
            name = it.name
        )
    }
}