package com.jairrab.domain.usercases

import com.jairrab.domain.Rndom
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.MapPoint
import com.jairrab.domain.model.Source
import com.jairrab.domain.repository.DomainRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class GetMapLocationsTest {

    private lateinit var getMapLocations: GetMapLocations

    private val postExecutionThread = mock<PostExecutionThread>()
    private val domainRepository = mock<DomainRepository>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMapLocations = GetMapLocations(postExecutionThread, domainRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMapPoints() {
        val list = listOf(
            MapPoint(
                Rndom.string(), Rndom.long(), Rndom.double(), Rndom.double(), Rndom.string()
            )
        )

        val mapInformation = MapInformation(list, Source.REMOTE, 123)

        whenever(domainRepository.getMapPoints()).thenReturn(Observable.just(mapInformation))

        val observer = getMapLocations.buildUseCaseObservable().test()

        val observedValue = observer.values().first() as MapInformation

        Assert.assertEquals(mapInformation.source, observedValue.source)
    }
}