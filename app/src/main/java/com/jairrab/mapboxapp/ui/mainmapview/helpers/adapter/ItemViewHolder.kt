package com.jairrab.mapboxapp.ui.mainmapview.helpers.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jairrab.domain.model.MapPoint
import com.jairrab.mapboxapp.databinding.ItemViewBinding
import com.jairrab.mapboxapp.ui.mainmapview.helpers.distance.DistanceUtils
import com.jairrab.presentation.MapControllerViewModel

internal class ItemViewHolder(
    private val binding: ItemViewBinding,
    private val distanceUtils: DistanceUtils,
    private val viewModel: MapControllerViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun updateRow(
        mapPoint: MapPoint
    ) {
        binding.mapPoint = mapPoint
        binding.viewModel = viewModel
        binding.distanceTv.updateDistance(mapPoint)
        binding.executePendingBindings()
    }

    private fun TextView.updateDistance(mapPoint: MapPoint) {
        viewModel.currentLocationMarker.value
            ?.let {
                visibility = View.VISIBLE
                text = distanceUtils.getDistanceMilesAndBearing(
                    latitude = mapPoint.latitude,
                    longitude = mapPoint.longitude,
                    currentLocation = it
                )
            } ?: let {
            visibility = View.GONE
        }
    }
}