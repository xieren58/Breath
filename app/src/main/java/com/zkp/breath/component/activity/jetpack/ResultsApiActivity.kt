package com.zkp.breath.component.activity.jetpack

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.databinding.ActivityRoomBinding

class ResultsApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale { activity, shouldRequest ->
                    Log.i("ResultsApiActivity_tag", "rationale")
                    shouldRequest?.again(true)
                }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(granted: MutableList<String>) {
                        val registerForActivityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                            ToastUtils.showShort("哈哈哈")

                        }
                        registerForActivityResult.launch(null)
                    }

                    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {

                    }
                }).request()
    }

    private class TakeDrawable(val context: Context) : ActivityResultContract<Void, Drawable>() {
        override fun createIntent(context: Context, input: Void?): Intent {
            return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Drawable? {
            if (null == intent || resultCode != Activity.RESULT_OK)
                return null
            if (intent == null || resultCode != Activity.RESULT_OK) return null;
            val bitmap = intent.getParcelableExtra<Bitmap>("data")
            return BitmapDrawable(context.resources, bitmap);
        }
    }

}