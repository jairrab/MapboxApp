package com.jairrab.mapboxapp.ui.utils

import android.view.View

fun View.setIsVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}