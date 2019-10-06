package com.jairrab.mapboxapp.ui.utils

import android.content.res.Resources

fun Float.convertToPixel(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return this * (metrics.densityDpi / 160f)
}

fun Float.convertToDp(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return this / (metrics.densityDpi / 160f)
}