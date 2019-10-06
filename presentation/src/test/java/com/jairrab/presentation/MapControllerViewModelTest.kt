package com.jairrab.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.MapPoint
import com.jairrab.domain.model.Source
import com.jairrab.domain.usercases.GetMapLocations
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.observers.DisposableObserver
import org.junit.*
import org.mockito.Captor


class MapControllerViewModelTest {

    private val getMapPoints = mock<GetMapLocations>()
    private val viewModel = MapControllerViewModel(getMapPoints)

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    val captor = argumentCaptor<DisposableObserver<MapInformation>>()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun downloadLocations() {

        viewModel.downloadLocations()

        verify(getMapPoints).execute(captor.capture(), eq(null))

        val mapPoints = listOf(
            MapPoint(
                Rndom.string(), Rndom.long(), Rndom.double(), Rndom.double(), Rndom.string()
            )
        )

        val mapInformation = MapInformation(
            mapPoints = mapPoints,
            source = Source.REMOTE,
            timeStamp = 200L
        )

        captor.firstValue.onNext(
            mapInformation
        )

        Assert.assertEquals(
            viewModel.mapInformationResponse.value,
            mapInformation
        )
    }
}