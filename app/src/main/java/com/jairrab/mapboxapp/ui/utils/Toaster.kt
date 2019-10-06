package com.jairrab.mapboxapp.ui.utils

import android.app.Application
import android.widget.Toast

class Toaster(private val application: Application) {

    fun showToast(
        resId: Int,
        duration: Int = Toast.LENGTH_LONG
    ) {
        showToast(application.getString(resId), duration)
    }

    fun showToast(
        message: String?,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(application, message, duration).show()
    }
}