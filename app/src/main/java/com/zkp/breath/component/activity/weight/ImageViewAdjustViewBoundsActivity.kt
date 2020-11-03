package com.zkp.breath.component.activity.weight

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityIvAdjustViewBoundsBinding


/**
 * https://www.jianshu.com/p/49f8d5e5965b
 *
 * adjustViewBounds :调整ImageView的边界，使得ImageView和图片有一样的长宽比例。adjustViewBounds只有在
 * ImageView一边固定，一边为wrap_content的时候才有意义。
 */
class ImageViewAdjustViewBoundsActivity : BaseActivity() {
    private lateinit var binding: ActivityIvAdjustViewBoundsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIvAdjustViewBoundsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val iv = binding.iv

        val bitmap1 = (iv.drawable as BitmapDrawable).bitmap
        val width1 = bitmap1.width
        val height1 = bitmap1.height

        val bitmap2 = ImageUtils.getBitmap(R.drawable.ic_iv_adjust_view_bounds)
        val width2 = bitmap2.width
        val height2 = bitmap2.height

        LogUtils.iTag("ImageViewAdjustViewBoundsActivity", "bitmap1宽：${width1}, bitmap1高：${height1};  "
                + "bitmap2宽：${width2}, bitmap2高：${height2}")

        SizeUtils.forceGetViewSize(iv) { view ->
            val width = view.width
            val height = view.height
            Log.i("获取测试宽", "width: ${width}, height:$height")

            // 控件宽高比
            val viewWhRatio = width.div(height * 1f)
            // 图片宽高比
            val bmpWhRatio = width1 * 1f / height1

            if (bmpWhRatio > viewWhRatio) {
                val fl = height * 1f / height1
                val resultBmpWidth = width1 * fl
                val resultBmpHeight = height1 * fl
                Log.i("", ": ");
            }
        }
    }

}