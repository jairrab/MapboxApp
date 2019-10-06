package com.jairrab.mapboxapp.ui.utils

import android.app.Activity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionsUtil @Inject constructor(
) : PermissionsListener {

    private val permissionsManager: PermissionsManager by lazy {
        PermissionsManager(this)
    }

    private var onGrantedCallBack: ((Boolean) -> Unit)? = null

    fun request(
        activity: Activity,
        onGrantedCallBack: ((Boolean) -> Unit)? = null
    ) {
        this.onGrantedCallBack = onGrantedCallBack

        permissionsManager.requestLocationPermissions(activity)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        //not needed now, intentionally left blank
    }

    override fun onPermissionResult(granted: Boolean) {
        onGrantedCallBack?.invoke(granted)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}