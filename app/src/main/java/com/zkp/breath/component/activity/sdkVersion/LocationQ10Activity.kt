package com.zkp.breath.component.activity.sdkVersion

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityLocationBinding


class LocationQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityLocationBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())

        varargSetClickListener(binding.tvLocation)
    }

    @SuppressLint("MissingPermission")
    private fun location() {
        //获取系统的LocationManager对象
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val stringBuilder = StringBuilder()
        //获取经度、纬度、等属性值
        stringBuilder.append("您的位置信息：\n")
        stringBuilder.append("经度：")
        stringBuilder.append(location?.longitude)
        stringBuilder.append("\n纬度：")
        stringBuilder.append(location?.latitude)

        Log.i("获取Location", "stringBuilder: $stringBuilder")
    }


    private fun requestPermission() {
//        PermissionUtils.permission(PermissionConstants.LOCATION)
        PermissionUtils.permission(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
                .rationale { activity, shouldRequest ->
                    Log.i(ACTIVITY_TAG, "rationale")
                    shouldRequest.again(true)
                }
                .callback(object : PermissionUtils.FullCallback {

                    override fun onGranted(granted: MutableList<String>) {
                        Log.i(ACTIVITY_TAG, "onGranted")
                        ActivityUtils.startHomeActivity()
                        handler.postDelayed({ location() }, 1000L)
                    }

                    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                        Log.i(ACTIVITY_TAG, "onDenied")
                    }
                }).request()
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvLocation == v) {
            requestPermission()
            return
        }
    }

}