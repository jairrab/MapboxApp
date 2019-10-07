package com.jairrab.mapboxapp.ui.mainmapview.helpers

import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Looper
import com.jairrab.mapboxapp.R
import com.jairrab.mapboxapp.ui.utils.PermissionsUtil
import com.jairrab.mapboxapp.ui.utils.Toaster
import com.jairrab.presentation.MapControllerViewModel
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import java.lang.ref.WeakReference
import javax.inject.Inject

internal class ViewLocation @Inject constructor(
    private val viewModel: MapControllerViewModel,
    private val toaster: Toaster,
    private val permissionsUtil: PermissionsUtil,
    private val locationEngine: LocationEngine
) : LocationEngineCallback<LocationEngineResult> {

    private var weakContext: WeakReference<Context?>? = null
    private var flyToLocation = false

    fun enableLocationComponent(
        context: Context,
        mapboxMap: MapboxMap?,
        flyToLocation: Boolean
    ) {
        weakContext = WeakReference(context)

        mapboxMap?.let {
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                onLocationPermissionGranted(
                    mapboxMap = mapboxMap,
                    context = this@ViewLocation.context,
                    flyToLocation = flyToLocation
                )
            } else {
                requestPermission(mapboxMap)
            }
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun requestLocationUpdate() {
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

        locationEngine.requestLocationUpdates(request, this, Looper.getMainLooper())
    }

    fun getLastLocation(mapboxMap: MapboxMap?) {
        context?.let { context ->
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                flyToLocation = true
                locationEngine.getLastLocation(this)
            } else {
                mapboxMap?.let { requestPermission(it) }
            }
        }
    }

    fun updateLocationMarker(location: Location? = null, mapboxMap: MapboxMap?) {
        mapboxMap?.locationComponent?.forceLocationUpdate(location)
    }

    override fun onSuccess(result: LocationEngineResult?) {
        context?.let {
            val lastLocation = result?.lastLocation ?: return

            viewModel.updateCurrentLocationMarker(lastLocation)
            viewModel.updateCurrentLocationStrip(lastLocation)

            if (flyToLocation) {
                flyToLocation = false
                viewModel.updateLocationFlyer(lastLocation)
            }
        }
    }

    override fun onFailure(exception: Exception) {
        viewModel.updateCurrentLocationStrip(null)
    }

    private fun requestPermission(mapboxMap: MapboxMap) {
        val permissionError = context?.getString(R.string.gps_toast_message)

        permissionsUtil.request(context as Activity) { granted ->
            if (granted) {
                onLocationPermissionGranted(
                    mapboxMap = mapboxMap,
                    context = this@ViewLocation.context,
                    flyToLocation = true
                )
            } else {
                toaster.showToast(permissionError)
                viewModel.updateCurrentLocationStrip(null)
            }
        }
    }

    private fun onLocationPermissionGranted(
        mapboxMap: MapboxMap,
        context: Context?,
        flyToLocation: Boolean
    ) {
        mapboxMap.style?.let { style ->
            if (context == null) return

            // Get an instance of the component
            val locationComponent = mapboxMap.locationComponent

            // Set the LocationComponent activation options
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(context, style)
                    .useDefaultLocationEngine(false)
                    .build()

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions)

            // Enable to make component visible
            locationComponent.isLocationComponentEnabled = true

            // Set the component's camera mode
            locationComponent.cameraMode = CameraMode.TRACKING

            // Set the component's render mode
            locationComponent.renderMode = RenderMode.COMPASS

            this.flyToLocation = flyToLocation

            if (flyToLocation) {
                requestLocationUpdate()
            }
        }
    }

    private val context
        get() = weakContext?.get()

    companion object {
        private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 3000L
        private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
    }
}