package com.jairrab.mapboxapp.ui.mainactivity

import android.os.Bundle
import com.jairrab.mapboxapp.R
import com.jairrab.mapboxapp.ui.BaseActivity
import com.jairrab.mapboxapp.ui.utils.PermissionsUtil
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject lateinit var permissionsUtil: PermissionsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
