package com.jairrab.mapboxapp.ui.mainmapview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jairrab.mapboxapp.R
import com.jairrab.mapboxapp.databinding.MyFragmentBinding
import com.jairrab.mapboxapp.ui.BaseFragment
import com.jairrab.mapboxapp.ui.mainmapview.helpers.ViewHelper
import com.jairrab.presentation.MapControllerViewModel
import com.jairrab.presentation.eventobserver.EventObserver
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainMapView : BaseFragment() {

    @Inject internal lateinit var helper: ViewHelper
    @Inject lateinit var viewModel: MapControllerViewModel

    private lateinit var binding: MyFragmentBinding
    private var mapboxMap: MapboxMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycle.addObserver(helper.map)

        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.my_fragment,
            container, false
        )

        viewModel.run {
            mapInformationResponse.observe(viewLifecycleOwner, Observer { information ->
                helper.map.updateMap(binding.offlineTv, mapboxMap, information)
                helper.list.setupRecyclerView(binding.recyclerview, information.mapPoints)
            })

            listItemClick.observe(viewLifecycleOwner, Observer {
                helper.map.onListItemClicked(it)
            })

            currentLocationMarker.observe(viewLifecycleOwner, Observer {
                helper.location.updateLocationMarker(it, mapboxMap)
            })

            currentLocationName.observe(viewLifecycleOwner, Observer {
                binding.currentLocationNameTv.text = it
                binding.currentLocationNameTv.visibility = View.VISIBLE
            })

            currentLocationGps.observe(viewLifecycleOwner, Observer {
                binding.currentLocationGpsTv.text = it
                binding.currentLocationGpsTv.visibility = View.VISIBLE
                binding.currentLocationLabelTv.visibility = View.VISIBLE
            })

            lastLocation.observe(viewLifecycleOwner, Observer {
                helper.location.getLastLocation(mapboxMap)
            })

            gpsFlasher.observe(viewLifecycleOwner, EventObserver {
                @Suppress("ConstantConditionIf")
                if (!ENABLE_LAT_LONG_FLASHER) return@EventObserver
                lifecycleScope.launch {
                    helper.map.flashCoordinatesDisplay(binding.latLongTv, it)
                }
            })

            locationFlyer.observe(viewLifecycleOwner, Observer {
                helper.map.flyToLocation(it, mapboxMap)
            })

            bottomSheetState.observe(viewLifecycleOwner, Observer {
                helper.slider.setState(it)
            })
        }

        binding.run {
            viewModel = this@MainMapView.viewModel

            mapView.onCreate(savedInstanceState)

            helper.map.setup(mapView) {
                binding.mapView.visibility = View.VISIBLE

                mapboxMap = it

                /*mapboxMap?.uiSettings?.setCompassMargins(0,
                    20f.convertToPixel().toInt(), 20f.convertToPixel().toInt(), 0)*/

                this@MainMapView.viewModel.downloadLocations()

                helper.location.enableLocationComponent(
                    context = requireContext(),
                    mapboxMap = mapboxMap,
                    flyToLocation = savedInstanceState == null
                )
            }

            helper.slider.setup(bottomSheet)

            return root
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    companion object {
        private const val ENABLE_LAT_LONG_FLASHER = false
    }
}