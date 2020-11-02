package com.zkp.breath.component.activity.weight

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
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
    }

}