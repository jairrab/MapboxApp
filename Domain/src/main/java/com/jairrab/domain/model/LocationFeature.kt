package com.jairrab.domain.model

data class LocationFeature(
    val address: String?,
    val bbox: List<Double>?,
    val center: List<Double>?,
    val id: String?,
    val place_name: String?,
    val place_type: List<String?>?,
    val relevance: Int?,
    val text: String?,
    val type: String?
)