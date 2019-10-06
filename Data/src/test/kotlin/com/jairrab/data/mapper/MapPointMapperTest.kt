package com.jairrab.data.mapper

import com.jairrab.data.Rndom
import com.jairrab.data.model.MapPointData
import com.jairrab.domain.model.MapPoint
import org.junit.Assert.assertEquals
import org.junit.Test

class MapPointMapperTest {


    private val mapper = MapPointMapper()

    @Test
    fun testMapToDomain() {
        val data = MapPointData(
            Rndom.string(),
            Rndom.long(),
            Rndom.double(),
            Rndom.double(),
            Rndom.string()
        )
        val domain = mapper.mapToDomain(data)
        assertEqualData(data, domain)
    }

    @Test
    fun testMapToData() {
        val domain = MapPoint(
            Rndom.string(),
            Rndom.long(),
            Rndom.double(),
            Rndom.double(),
            Rndom.string()
        )
        val data = mapper.mapToData(domain)
        assertEqualData(data, domain)
    }

    private fun assertEqualData(
        d1: MapPointData,
        d2: MapPoint
    ) {
        assertEquals(d1.id, d2.id)
        assertEquals(d1.name, d2.name)
        assertEquals(d1.latitude, d2.latitude, 0.001)
        assertEquals(d1.longitude, d2.longitude, 0.001)
        assertEquals(d1.description, d2.description)
    }
}
