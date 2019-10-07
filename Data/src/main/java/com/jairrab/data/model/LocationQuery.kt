package com.jairrab.data.model

import com.jairrab.data.model.locationquery.Feature

data class LocationQuery(
    val attribution: String,
    val features: List<Feature>,
    val query: List<Double>,
    val type: String
)