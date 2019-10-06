package com.jairrab.mapboxapp.ui.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View

fun Activity.transparentizeStatusBar() {
    window.run {
        decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        statusBarColor = Color.TRANSPARENT
    }
}