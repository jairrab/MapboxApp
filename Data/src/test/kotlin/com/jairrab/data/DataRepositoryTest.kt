package com.jairrab.data

import com.jairrab.data.mapper.MapPointMapper
import com.jairrab.data.model.MapPointData
import com.jairrab.data.store.CacheDataStore
import com.jairrab.data.store.DataStore
import com.jairrab.data.store.RemoteDataStore
import com.jairrab.data.util.TimeUtils
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.Source
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DataRepositoryTest {

    private val remoteDataStore = mock<RemoteDataStore>()
    private val cacheDataStore = mock<CacheDataStore>()
    private val timeUtils = mock<TimeUtils>()

    private lateinit var dataStore: DataStore
    private lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataStore = DataStore(cacheDataStore, remoteDataStore)
        dataRepository = DataRepository(dataStore, timeUtils, MapPointMapper())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMapPoints() {

        val description = Rndom.string()
        val id = Rndom.long()
        val latitude = Rndom.double()
        val longitude = Rndom.double()
        val name = Rndom.string()

        val mapPointData = MapPointData(
            description = description,
            id = id,
            latitude = latitude,
            longitude = longitude,
            name = name
        )

        val list: List<MapPointData> = listOf(
            mapPointData
        )

        whenever(timeUtils.currentTime)
            .thenReturn(1234567L)

        whenever(remoteDataStore.getMapPoints())
            .thenReturn(Observable.just(list))

        whenever(cacheDataStore.getMapPoints())
            .thenReturn(Observable.just(list))

        whenever(cacheDataStore.saveMapPoints(any()))
            .thenReturn(Completable.complete())

        whenever(cacheDataStore.updateLastLocationTimeStamp(any()))
            .thenReturn(Completable.complete())

        whenever(cacheDataStore.getLastLocationTimeStamp())
            .thenReturn(Observable.just(1234L))

        val observer: TestObserver<MapInformation> = dataRepository.getMapPoints().test()

        val observedValue = observer.values().first() as MapInformation

        Assert.assertEquals(observedValue.source, Source.REMOTE)
        Assert.assertEquals(observedValue.mapPoints[0].name, name)
        Assert.assertEquals(observedValue.mapPoints[0].description, description)
        Assert.assertEquals(observedValue.mapPoints[0].id, id)
        Assert.assertEquals(observedValue.mapPoints[0].latitude, latitude, 0.001)
        Assert.assertEquals(observedValue.mapPoints[0].longitude, longitude, 0.001)
        Assert.assertEquals(observedValue.timeStamp, 1234567L)
    }

    @Test
    fun getMapPointsWithError() {

        val description = Rndom.string()
        val id = Rndom.long()
        val latitude = Rndom.double()
        val longitude = Rndom.double()
        val name = Rndom.string()

        val mapPointData = MapPointData(
            description = description,
            id = id,
            latitude = latitude,
            longitude = longitude,
            name = name
        )

        val list: List<MapPointData> = listOf(
            mapPointData
        )

        whenever(remoteDataStore.getMapPoints())
            .thenReturn(Observable.error(Exception()))

        whenever(cacheDataStore.getMapPoints())
            .thenReturn(Observable.just(list))

        whenever(cacheDataStore.saveMapPoints(any()))
            .thenReturn(Completable.complete())

        whenever(cacheDataStore.updateLastLocationTimeStamp(any()))
            .thenReturn(Completable.complete())

        whenever(cacheDataStore.getLastLocationTimeStamp())
            .thenReturn(Observable.just(1234L))

        val observer: TestObserver<MapInformation> = dataRepository.getMapPoints().test()

        val observedValue = observer.values().first() as MapInformation

        Assert.assertEquals(observedValue.source, Source.CACHE)
        Assert.assertEquals(observedValue.mapPoints[0].name, name)
        Assert.assertEquals(observedValue.mapPoints[0].description, description)
        Assert.assertEquals(observedValue.mapPoints[0].id, id)
        Assert.assertEquals(observedValue.mapPoints[0].latitude, latitude, 0.001)
        Assert.assertEquals(observedValue.mapPoints[0].longitude, longitude, 0.001)
        Assert.assertEquals(observedValue.timeStamp, 1234L)
    }
}