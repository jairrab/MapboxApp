package com.jairrab.data.model.locationquery

data class Feature(
    val address: String?,
    val bbox: List<Double>?,
    val center: List<Double>?,
    val context: List<Context>?,
    val geometry: Geometry?,
    val id: String?,
    val place_name: String?,
    val place_type: List<String?>?,
    val properties: Properties?,
    val relevance: Int?,
    val text: String?,
    val type: String?
)