package com.zkp.breath.component.activity.sdkVersion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.PermissionUtils
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityDeviceInfoBinding
import java.lang.reflect.UndeclaredThrowableException

/**
 * 从Android10开始，普通应用不再允许请求权限android.permission.READ_PHONE_STATE（没有权限弹框，直接回调成功），
 * 无论你的App是否适配过Android Q（既targetSdkVersion是否大于等于29），mac地址、IMEI等设备信息标识设备的方
 * 法开始统统失效，targetSdkVersion>=29 的应用，其在获取设备信息时，会直接抛出异常。
 */
class DeviceInfoQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityDeviceInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uniqueDeviceId = DeviceUtils.getUniqueDeviceId()
        val macAddress = DeviceUtils.getMacAddress()
        Log.i("获取设备信息", "uniqueDeviceId: ${uniqueDeviceId}, macAddress: ${macAddress}")

        val manager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PermissionUtils.permission(Manifest.permission.READ_PHONE_STATE)
                    .rationale { activity, shouldRequest ->
                        Log.i(ACTIVITY_TAG, "rationale")
                        shouldRequest.again(true)
                    }
                    .callback(object : PermissionUtils.FullCallback {
                        @SuppressLint("MissingPermission")
                        override fun onGranted(granted: MutableList<String>) {
                            Log.i(ACTIVITY_TAG, "onGranted")
                            try {
                                val imei = manager.getImei()
                                val meid = manager.meid
                                val deviceId = manager.getDeviceId()
                                Log.i("获取设备信息", "imei: ${imei}, meid:${meid}, deviceId:${deviceId}")
                            } catch (e: Exception) {
                            }
                        }

                        override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                            Log.i(ACTIVITY_TAG, "onDenied")
                        }
                    }).request()

        }
    }

    override fun onDebouncingClick(v: View) {

    }

}