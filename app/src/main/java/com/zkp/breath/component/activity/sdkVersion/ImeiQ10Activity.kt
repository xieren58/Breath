package com.zkp.breath.component.activity.sdkVersion

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.bun.miitmdid.core.ErrorCode
import com.bun.miitmdid.core.MdidSdkHelper
import com.bun.miitmdid.interfaces.IIdentifierListener
import com.bun.miitmdid.interfaces.IdSupplier
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityImeiBinding

/**
 * oaid官网：http://www.msa-alliance.cn/col.jsp?id=120
 * 1. oaid推荐使用最新版本，最新版本的适配手机范围更广泛，但是最新版本需要注册账号，需要填写资料和上传营业执照，成功后还需要申请应用对应的证书，在sdk初始化的时候
 * 会进行证书有效性的检查，证书有效期为1年。
 * 2. demo使用的版本是1.0.25，发现在android11的小米10手机上，时而可以时而不可以。猜想和oaid版本有关系
 * 3. 获取oaid为耗时任务，不需要连网，但还是推荐进行缓存。
 * 4. 不同厂商的支持是不同的，有些是android版本在10及其以上支持，有些是自己的系统版本（小米，华为，vivo，oppo），一般在实际开发中，imei和oaid最好都上传。
 *
 * imei：国际移动设备识别码
 * 0. 从 Android Q 开始，应用必须具有 READ_PRIVILEGED_PHONE_STATE 签名权限才能访问设备的不可重置标识符（包含 IMEI 和序列号），原来的READ_PHONE_STATE权限
 *    已经不能获得IMEI和序列号。国内手机使用oaid进行替代
 * 1. imei被列为用户隐私信息，以及存在被修改的风险，在androidQ（10）及其以上即便申请权限获取也无法再获取。
 * 2. 低版本还是可以申请权限进行获取，当权限被拒，获取无效（不同的版本可能是抛出异常或者返回null，空字符串等），记得try-catch
 */
class ImeiQ10Activity : ClickBaseActivity() {

    private lateinit var binding: ActivityImeiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImeiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        varargSetClickListener(binding.tvImei)
    }

    override fun onDebouncingClick(v: View) {
        if (binding.tvImei == v) {
            getOaid()
            getIMEI()
            return
        }
    }

    private fun getIMEI() {
        PermissionUtils.permission(Manifest.permission.READ_PHONE_STATE)
            .rationale { activity, shouldRequest ->
                Log.i(ACTIVITY_TAG, "rationale")
                shouldRequest?.again(true)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    Log.i(ACTIVITY_TAG, "onGranted")
                    val imei = PhoneUtils.getIMEI()
                    Log.i("测试获取imei", "imei: $imei")
                }

                override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
                    Log.i(ACTIVITY_TAG, "onDenied")
                }
            }).request()
    }

    /**
     * 获取oaid
     * 注意：oaid_sdk_1.0.25.aar版本，在android11的小米10的手机上，时而可以时而不可以，猜想和oaid的版本有关（最新的是1.0.30）。
     */
    private fun getOaid() {
        val initSdk = try {
            MdidSdkHelper.InitSdk(this, true, object : IIdentifierListener {
                override fun OnSupport(p0: Boolean, p1: IdSupplier?) {
                    // 子线程回调
                    if (p1?.isSupported == true) {
                        val oaid = p1.oaid
                        Log.v("测试获取oaid", "oaid: $oaid, 是否主线程: ${ThreadUtils.isMainThread()}")
                        ToastUtils.showShort("oaid: $oaid, 是否主线程: ${ThreadUtils.isMainThread()}")
                    }
                }
            })
        } catch (e: Exception) {
            -1
        }
        when (initSdk) {
            //不支持的设备
            ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT -> {
            }
            //加载配置文件出错
            ErrorCode.INIT_ERROR_LOAD_CONFIGFILE -> {
            }
            //不支持的设备厂商
            ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT -> {
            }
            //获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程
            ErrorCode.INIT_ERROR_RESULT_DELAY -> {
            }
            //反射调用出错
            ErrorCode.INIT_HELPER_CALL_ERROR -> {
            }
        }
    }

}