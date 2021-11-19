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

/**
 * 1.Android 10 之前只有ACCESS_FINE_LOCATION和ACCESS_COARSE_LOCATION；
 * 2.Android 10 新增加了后台定位权限：ACCESS_BACKGROUND_LOCATION，该权限对应始终允许；老的权限：ACCESS_FINE_LOCATION和ACCESS_COARSE_LOCATION代表仅前台使用允许；
 * 3.应用的targetSdkVersion<Q，谷歌提供了兼容性方案，只要应用申请了老的位置权限ACCESS_FINE_LOCATION或者ACCESS_COARSE_LOCATION，
 *   会默认请求ACCESS_BACKGROUND_LOCATION权限，动态授权弹框参考下面第一个图。
 * 4.应用的TargetSdkVersion>=Q，如果应用必须要始终定位，可以只申请ACCESS_BACKGROUND_LOCATION即可，权限弹框参考下面第三个图；
 *   如果应用只需要申请前台定位，则只需要申请老的定位权限即可，具体授权弹框参考第二个图。如果都申请则出现三态权限弹框，参考下面第一个图。
 *
 * https://lbs.amap.com/api/android-location-sdk/guide/utilities/permision_10
 *   https://lbs.amap.com/api/android-location-sdk/guide/utilities/permision_11
 */
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