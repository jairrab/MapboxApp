package com.jairrab.mapboxapp.ui.mainmapview.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jairrab.domain.model.MapPoint
import com.jairrab.mapboxapp.ui.mainmapview.helpers.adapter.RecyclerAdapter
import javax.inject.Inject

class ViewList @Inject constructor(
    private val recyclerAdapter: RecyclerAdapter
) {

    fun setupRecyclerView(recyclerView: RecyclerView, mapPoints: List<MapPoint>) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setHasFixedSize(true)

        recyclerAdapter.mapPoints = mapPoints
        recyclerView.adapter = recyclerAdapter
    }
}