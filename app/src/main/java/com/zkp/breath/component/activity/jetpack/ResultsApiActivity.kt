package com.zkp.breath.component.activity.jetpack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.hi.dhl.binding.viewbind
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityResultsApiBinding
import com.zkp.breath.databinding.ActivityResultsApiSecondBinding
import com.zkp.breath.jetpack.results.CustomActivityResultContract
import com.zkp.breath.jetpack.results.ResultApiLifecycleObserver
import com.zkp.breath.utils.getVideoDuration
import java.io.File

/**
 *
 * https://juejin.cn/post/6887743061309587463
 *
 * onActivityResult的缺点：
 * 1. 各种处理结果都耦合在该回调里，并且还得定义一堆额外的常量REQUEST_CODE，用与判断是哪个请求的回调结果。
 * 2. 所有的result结果都需要在fragment或者activity监听，其他的类需要通过回调等方式才能间接获取result结果。
 *
 * registerForActivityResult:
 * 1.其实就是把一个startActivityForResult和onActivityResult放在了一个协议中，可读性增强，不再需要定义
 *   requestCode。
 * 2.ActivityResultContracts定义了常用的result协议，基本满足日常需求。
 */
class ResultsApiActivity : ClickBaseActivity() {

    companion object {
        val PICK_VIDEO_REQ_CODE = 66
    }

    private val binding by viewbind<ActivityResultsApiBinding>()
    private lateinit var observer: ResultApiLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvAction.setOnClickListener(this)

        observer = ResultApiLifecycleObserver(activityResultRegistry)
        lifecycle.addObserver(observer)
    }

    override fun onDebouncingClick(v: View) {
        if (v == binding.tvAction) {
            action()
        }
    }

    private fun action() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
            .setGravityCenter(true)
            .addItem("Custom_ActivityResultContract")
            .addItem("startActivityForResult_Contract")
            .addItem("requestPermissionContract")
            .addItem("requestMultiplePermissionContract")
            .addItem("takePicturePreviewContract")
            .addItem("takePictureContract")
            .addItem("takeVideoContract")
            .addItem("pickContract")
            .addItem("contentContract")
            .addItem("onActivityResultPickVideo")
            .addItem("multipleContentContract")
            .addItem("createDocumentContract")
            .addItem("LifecycleObserver")
            .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                when (tag) {
                    "Custom_ActivityResultContract" -> {
                        customContract()
                    }
                    "startActivityForResult_Contract" -> {
                        startActivityForResultContract()
                    }
                    "requestPermissionContract" -> {
                        requestPermissionContract()
                    }
                    "requestMultiplePermissionContract" -> {
                        requestMultiplePermissionContract()
                    }
                    "takePicturePreviewContract" -> {
                        takePicturePreviewContract()
                    }
                    "takePictureContract" -> {
                        takePictureContract()
                    }
                    "takeVideoContract" -> {
                        takeVideoContract()
                    }
                    "pickContract" -> {
                        pickContract()
                    }
                    "contentContract" -> {
                        contentContract()
                    }
                    "onActivityResultPickVideo" -> {
                        onActivityResultPickVideo(this, PICK_VIDEO_REQ_CODE)
                    }
                    "multipleContentContract" -> {
                        multipleContentContract()
                    }
//                        "createDocumentContract" -> {
//                        }
                    "LifecycleObserver" -> {
                        observer.selectImage()
                    }
                }
            }
            .build()
            .show()
    }

    /**
     * 选择一组内容，返回一个通过ContentResolver#openInputStream(Uri)访问原生数据的Uri地址（content://形式）。
     */
    private fun multipleContentContract() {
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { result ->
            Log.i("multipleContentContract", "UriList: $result")
        }.launch("image/*")
    }

    /**
     * 选择一条内容，返回一个通过ContentResolver#openInputStream(Uri)访问原生数据的Uri地址（content://形式）。
     */
    private fun contentContract() {
        //调用图库，获取所有本地图片：image/*
        //调用音乐，获取所有本地音乐文件：audio/*
        //调用图库，获取所有本地视频文件：video/*

        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.i("contentContract", "Uri: $result")
        }.launch("image/*")
    }

    /**
     * 配合onActivityResult，选择本地相册的视频的demo
     */
    fun onActivityResultPickVideo(context: Activity, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*")
        context.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQ_CODE) {
            val selectedVideo = data?.data
            val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
            val cursor = contentResolver.query(
                selectedVideo!!, filePathColumn, null, null, null
            )
            cursor?.moveToFirst()
            val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
            val videoPath = cursor!!.getString(columnIndex)
            cursor!!.close()
            val size = FileUtils.getSize(videoPath)
            val localVideoDuration: Long = getVideoDuration(videoPath)
            Log.i(
                "视频选择",
                "videoPath: ${videoPath}, size: ${size}, localVideoDuration: ${localVideoDuration}"
            )
        }
    }

    /**
     * 通讯录选择联系人，返回Uri
     */
    private fun pickContract() {
        registerForActivityResult(ActivityResultContracts.PickContact()) { result ->
            Log.i("pickContract", "Uri: $result")
        }.launch(null)
    }

    /**
     * 录制视频,返回一张缩略图的例子
     *
     * 1. 无论launch()的参数传入Uri还是null，都会回调
     * 2. 如果不传入则自动保存在相册中，但是目前回调无法提供视频信息。
     * 3. 如果指定具体的Uri，不会同步到相册中，回调也无法提供视频信息。
     * 4. 并没有返回缩略图，不知是手机问题还是目前alpha版不稳定的问题
     */
    private fun takeVideoContract() {
        // 内部存储
//        val file = File(PathUtils.getInternalAppCachePath() + "/takeVideo1.mp4")
        // 外部存储
        val file = File(PathUtils.getExternalAppCachePath() + "/takeVideo1.mp4")
        FileUtils.createFileByDeleteOldFile(file)
        val file2Uri = UriUtils.file2Uri(file)

        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { activity, shouldRequest ->
                Log.i("ResultsApiActivity_tag", "rationale")
                shouldRequest.again(true)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    registerForActivityResult(ActivityResultContracts.TakeVideo()) { result ->
                        Log.i("takeVideoContract", "result: $result")
                    }.launch(null)
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {

                }
            }).request()
    }

    /**
     * 拍照预览后选择是否回调的例子。
     *
     * 如果launch()的参数传入Uri，则不会触发回调；
     * 如果launch()的参数传入null，则触发回调并返回Bitmap；
     *
     * 缺点：实际开发中，需要既能保存到文件夹，又能触发回调。我们可以使用触发回调的方式来进行手动保存，这样就能解决问题。
     */
    private fun takePictureContract() {
        // 内部存储
//        val file = File(PathUtils.getInternalAppCachePath() + "/takePicture1.jpg")
        // 外部存储
        val file = File(PathUtils.getExternalAppCachePath() + "/takePicture1.jpg")

        FileUtils.createFileByDeleteOldFile(file)
        val file2Uri = UriUtils.file2Uri(file)

        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { activity, shouldRequest ->
                Log.i("ResultsApiActivity_tag", "rationale")
                shouldRequest.again(true)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
                        if (result != null) {
                            Log.i("takePictureContract", "file: $file")
                        }
                    }.launch(file2Uri)
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {

                }
            }).request()
    }

    /**
     * 拍照预览后获取bitmap的例子
     */
    private fun takePicturePreviewContract() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { activity, shouldRequest ->
                Log.i("ResultsApiActivity_tag", "rationale")
                shouldRequest.again(true)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
                        if (result != null) {
                            ToastUtils.showShort("拍照: 预览")
                        }
                    }.launch(null)
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {

                }
            }).request()
    }

    /**
     * 请求多个权限的例子
     *
     * 缺点：1. 拒绝没有回调
     *      2. 第一次全部成功不会回调，后面才会回调。
     *
     * 总结：完全不适用于实际开发。
     */
    private fun requestMultiplePermissionContract() {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            for ((key, value) in result) {
                Log.i("请求多个权限的例子", "the element at $key is $value")
            }
        }.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
    }

    /**
     * 请求单个权限的例子
     *
     * 缺点：1. 拒绝没有回调
     *      2. 第一次成功不会回调，后面才会回调。
     *
     * 总结：完全不适用于实际开发。
     */
    private fun requestPermissionContract() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            ToastUtils.showShort("申请权限: $result")
        }.launch(Manifest.permission.CAMERA)
    }

    /**
     * 跳转其他Activity获取result的例子
     */
    private fun startActivityForResultContract() {
        val intent = Intent(this, ResultsApiSecondActivity::class.java).apply {
            putExtra("name", "Hello,技术最TOP")
        }

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK && data != null) {
                ToastUtils.showShort("回传数据：$data")
            }
        }.launch(intent)
    }

    /**
     * 自定义协议的例子
     */
    private fun customContract() {
        registerForActivityResult(CustomActivityResultContract()) { result ->
            ToastUtils.showShort("回传数据：$result")
        }.launch("Hello,技术最TOP")
    }

    class ResultsApiSecondActivity : BaseActivity() {
        private val binding by viewbind<ActivityResultsApiSecondBinding>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val name = intent.getStringExtra("name")
            Log.i("接收Tag", "接收到的数据为：$name")

            val intent = Intent().apply {
                putExtra("result", "Hello，依然范特西稀，我是回传的数据！")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

//    private class TakeDrawable(val context: Context) : ActivityResultContract<Void, Drawable>() {
//        override fun createIntent(context: Context, input: Void?): Intent {
//            return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Drawable? {
//            if (null == intent || resultCode != Activity.RESULT_OK)
//                return null
//            if (intent == null || resultCode != Activity.RESULT_OK) return null;
//            val bitmap = intent.getParcelableExtra<Bitmap>("data")
//            return BitmapDrawable(context.resources, bitmap);
//        }
//    }

}