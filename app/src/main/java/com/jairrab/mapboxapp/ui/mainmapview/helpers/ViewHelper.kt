package com.jairrab.mapboxapp.ui.mainmapview.helpers

import com.jairrab.mapboxapp.di.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class ViewHelper @Inject constructor(
    val map: ViewMap,
    val list: ViewList,
    val slider: ViewSlider,
    val location: ViewLocation
)