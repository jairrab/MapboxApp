package com.jairrab.mapboxapp.ui.mainmapview.helpers.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jairrab.domain.model.MapPoint
import com.jairrab.mapboxapp.databinding.ItemViewBinding
import com.jairrab.presentation.MapControllerViewModel

class ItemViewHolder(
    private val binding: ItemViewBinding,
    private val viewModel: MapControllerViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun updateRow(
        mapPoint: MapPoint
    ) {
        binding.mapPoint = mapPoint
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}