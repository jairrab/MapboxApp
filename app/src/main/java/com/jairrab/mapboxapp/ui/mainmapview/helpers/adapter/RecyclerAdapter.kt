package com.jairrab.mapboxapp.ui.mainmapview.helpers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jairrab.domain.model.MapPoint
import com.jairrab.mapboxapp.R
import com.jairrab.mapboxapp.ui.mainmapview.helpers.distance.DistanceUtils
import com.jairrab.presentation.MapControllerViewModel
import javax.inject.Inject

internal class RecyclerAdapter @Inject constructor(
    private val distanceUtils: DistanceUtils,
    private val viewModel: MapControllerViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mapPoints: List<MapPoint>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view, parent, false
            ),
            distanceUtils = distanceUtils,
            viewModel = viewModel
        )
    }

    override fun getItemCount() = mapPoints.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.updateRow(mapPoints[position])
    }

}