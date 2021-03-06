package com.jairrab.presentation

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jairrab.domain.model.LocationFeature
import com.jairrab.domain.model.MapInformation
import com.jairrab.domain.model.MapPoint
import com.jairrab.domain.usercases.GetLocationQuery
import com.jairrab.domain.usercases.GetMapLocations
import com.jairrab.presentation.eventobserver.Event
import com.jairrab.presentation.utils.roundToNDecimal
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class MapControllerViewModel @Inject constructor(
    private val getMapLocations: GetMapLocations,
    private val getLocationQuery: GetLocationQuery
) : ViewModel() {

    private val _listItemClick = MutableLiveData<MapPoint>()
    val listItemClick: LiveData<MapPoint> = _listItemClick

    private val _currentLocationName = MutableLiveData<String>()
    val currentLocationName: LiveData<String> = _currentLocationName

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility

    private val _currentLocationMarker = MutableLiveData<Location>()
    val currentLocationMarker: LiveData<Location> = _currentLocationMarker

    private val _currentLocationGps = MutableLiveData<String?>()
    val currentLocationGps: LiveData<String?> = _currentLocationGps

    private val _gpsFlasher = MutableLiveData<Event<MapPoint>>()
    val gpsFlasher: LiveData<Event<MapPoint>> = _gpsFlasher

    private val _locationFlyer = MutableLiveData<Location>()
    val locationFlyer: LiveData<Location> = _locationFlyer

    private val _bottomSheetState = MutableLiveData<Int>()
    val bottomSheetState: LiveData<Int> = _bottomSheetState

    private val _lastLocation = MutableLiveData<Event<Boolean>>()
    val lastLocation: LiveData<Event<Boolean>> = _lastLocation

    private val _mapInformationResponse = MutableLiveData<MapInformation>()
    val mapInformationResponse: LiveData<MapInformation> = _mapInformationResponse

    fun onListItemClicked(mapPoint: MapPoint) {
        _listItemClick.value = mapPoint
    }

    fun updateCurrentLocationMarker(location: Location) {
        _currentLocationMarker.value = location
    }

    fun updateCurrentLocationStrip(location: Location?) {
        _currentLocationGps.value = (
                if (location != null) {
                    val lat = location.latitude.roundToNDecimal(4)
                    val long = location.longitude.roundToNDecimal(4)
                    "Lat: $lat, Long: $long"
                } else {
                    "No GPS detected"
                })
    }

    fun updateLocationFlyer(location: Location) {
        _locationFlyer.value = location
    }

    fun updateGpsFlasher(point: MapPoint) {
        _gpsFlasher.value = Event(point)
    }

    fun updateBottomSheetState(state: Int) {
        _bottomSheetState.value = state
    }

    fun goToLastLocation() {
        _lastLocation.value = Event(true)
    }

    fun downloadLocations() {
        getMapLocations.execute(object : DisposableObserver<MapInformation>() {
            override fun onComplete() {
            }

            override fun onNext(t: MapInformation) {
                _mapInformationResponse.value = t
            }

            override fun onError(e: Throwable) {
                //not needed now, all errors are checked in data layer, intentionally left blank
            }

        })
    }

    fun getCurrentLocation(lastLocation: Location) {
        setGpsScanningIndicatorVisiblity(true)

        getLocationQuery.execute(object : DisposableObserver<LocationFeature>() {
            override fun onComplete() {
                setGpsScanningIndicatorVisiblity(false)
            }

            override fun onNext(t: LocationFeature) {
                _currentLocationName.value = t.place_name
            }

            override fun onError(e: Throwable) {
                //not needed now, all errors are checked in data layer, intentionally left blank
            }

        }, Pair(lastLocation.latitude, lastLocation.longitude))
    }

    fun setGpsScanningIndicatorVisiblity(visible:Boolean){
        _progressBarVisibility.value = visible
    }
}