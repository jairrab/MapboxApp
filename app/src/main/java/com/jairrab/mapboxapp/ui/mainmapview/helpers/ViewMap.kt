package com.jairrab.mapboxapp.ui.mainmapview.helpers

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Location
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.MapPoint
import com.jairrab.domain.model.Source
import com.jairrab.mapboxapp.R
import com.jairrab.mapboxapp.ui.mainmapview.helpers.distance.DistanceUtils
import com.jairrab.mapboxapp.ui.mainmapview.mapper.Mapper
import com.jairrab.mapboxapp.ui.utils.TimeUtils
import com.jairrab.mapboxapp.ui.utils.Toaster
import com.jairrab.presentation.MapControllerViewModel
import com.jairrab.presentation.utils.roundToNDecimal
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.coroutines.delay
import java.lang.ref.WeakReference
import javax.inject.Inject

internal class ViewMap @Inject constructor(
    private val viewModel: MapControllerViewModel,
    private val timeUtils: TimeUtils,
    private val distanceUtils: DistanceUtils,
    private val toaster: Toaster,
    private val mapper: Mapper
) : LifecycleObserver {

    private var weakMapView: WeakReference<MapView?>? = null

    fun setup(mapView: MapView?, callback: (MapboxMap) -> Unit) {
        weakMapView = WeakReference(mapView)

        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                callback(mapboxMap)
            }
        }
    }

    fun flyToLocation(location: Location, mapboxMap: MapboxMap?) {
        val position = CameraPosition.Builder()
            .target(mapper.mapToLatLng(location))
            .zoom(15.0)
            .tilt(20.0)
            .build()

        mapboxMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(position),
            1000
        )
    }

    fun onListItemClicked(point: MapPoint) {
        viewModel.updateLocationFlyer(mapper.mapToLocation(point))
        viewModel.updateGpsFlasher(point)
        viewModel.updateBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    @SuppressLint("SetTextI18n")
    suspend fun flashCoordinatesDisplay(textView: TextView?, point: MapPoint) {
        textView?.visibility = View.VISIBLE
        val lat = point.latitude.roundToNDecimal(5)
        val long = point.longitude.roundToNDecimal(5)
        textView?.text = "${point.name}\n$lat : $long"
        delay(3000)
        textView?.visibility = View.GONE
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {
        val mapView = weakMapView?.get()
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (event) {
            Lifecycle.Event.ON_START   -> mapView?.onStart()
            Lifecycle.Event.ON_RESUME  -> mapView?.onResume()
            Lifecycle.Event.ON_PAUSE   -> mapView?.onPause()
            Lifecycle.Event.ON_STOP    -> mapView?.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
        }
    }

    fun updateMap(
        offlineTv: TextView,
        mapboxMap: MapboxMap?,
        information: MapInformation
    ) {
        if (information.source == Source.CACHE) {
            offlineTv.visibility = View.VISIBLE
            offlineTv.text = String.format(
                "%s. %s: %s", "Server offline", "Last Update",
                timeUtils.getElapsedString(information.timeStamp)
            )
        } else {
            offlineTv.visibility = View.GONE
        }

        offlineTv.context?.getDrawable(R.drawable.ic_location_on_red_24dp)?.let { drawable ->
            addMarkerViews(
                points = information.mapPoints,
                mapboxMap = mapboxMap,
                drawable = drawable
            )
        }
    }

    private fun addMarkerViews(
        points: List<MapPoint>,
        mapboxMap: MapboxMap?,
        drawable: Drawable
    ) {
        mapboxMap?.style?.run {
            addImage(MARKER_ID, drawable)

            addSource(
                GeoJsonSource(SOURCE_ID, mapper.mapToFeatureCollections(points))
            )

            addLayer(SymbolLayer(LAYER_MARKER, SOURCE_ID).apply {
                setProperties(
                    PropertyFactory.iconImage(MARKER_ID),
                    PropertyFactory.iconOffset(arrayOf(0f, -9f)),
                    PropertyFactory.textField("{name}"),
                    PropertyFactory.textColor(Color.RED),
                    PropertyFactory.textAnchor(Property.TEXT_ANCHOR_TOP)
                )
            })
        }

        mapboxMap?.addOnMapClickListener { latLng ->
            val screenPoint = mapboxMap.projection.toScreenLocation(latLng)
            val features = mapboxMap.queryRenderedFeatures(screenPoint, LAYER_MARKER)

            if (features.isNotEmpty()) {
                val selectedFeature = features[0]
                val description = selectedFeature.getStringProperty("description")
                val point = selectedFeature.geometry() as Point
                val latLong = point.coordinates()
                val latitude = latLong[1].roundToNDecimal(5)
                val longitude = latLong[0].roundToNDecimal(5)
                val currentLocation = viewModel.currentLocationMarker.value
                val distance = currentLocation?.let {
                    distanceUtils.getDistanceMilesAndBearing(
                        point1 = point,
                        point2 = mapper.mapToPoint(currentLocation)
                    )
                }

                toaster.showToast(
                    "$description\n" +
                            "Coordinates: $latitude:$longitude" +
                            (distance?.let { "\n$it from current location" } ?: "")
                )
            }
            true
        }
    }

    companion object {
        const val LAYER_MARKER = "LAYER_MARKER"
        const val SOURCE_ID = "SOURCE_ID"
        const val MARKER_ID = "MARKER_ID"
    }

}