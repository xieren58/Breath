package com.zkp.breath.component.activity.weight

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ScreenUtils
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityIvScaleTypeBinding


class ImageViewScaleTypeActivity : BaseActivity() {

    companion object {
        const val WH_SCALE_1_1 = "1:1"
        const val WH_SCALE_9_16 = "9:16"
        const val WH_SCALE_16_9 = "16:9"

        const val SCALE_TYPE_FIT_XY = "fit_xy"
        const val SCALE_TYPE_FIT_START = "fit_start"
        const val SCALE_TYPE_FIT_CENTER = "fit_center"
        const val SCALE_TYPE_FIT_END = "fit_end"
        const val SCALE_TYPE_CENTER = "center"
        const val SCALE_TYPE_CENTER_CROP = "center_crop"
        const val SCALE_TYPE_CENTER_INSIDE = "center_inside"
    }

    private lateinit var binding: ActivityIvScaleTypeBinding
    private var currentIvWhScale = WH_SCALE_1_1
    private var currentIvAttrScaleType = SCALE_TYPE_FIT_CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIvScaleTypeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvWhScale.setOnClickListener(onClick)
        binding.tvScaleType.setOnClickListener(onClick)
        binding.tvPic.setOnClickListener(onClick)
        info()
    }

    private val onClick: ClickUtils.OnDebouncingClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            when (v) {
                binding.tvWhScale -> {
                    ivWhScale()
                    return
                }

                binding.tvScaleType -> {
                    ivAttrScaleType()
                    return
                }

                binding.tvPic -> {
                    picWhScale()
                    return
                }

            }
        }
    }

    private fun ivAttrScaleType() {

        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem(SCALE_TYPE_FIT_XY)
                .addItem(SCALE_TYPE_FIT_START)
                .addItem(SCALE_TYPE_FIT_CENTER)
                .addItem(SCALE_TYPE_FIT_END)
                .addItem(SCALE_TYPE_CENTER)
                .addItem(SCALE_TYPE_CENTER_CROP)
                .addItem(SCALE_TYPE_CENTER_INSIDE)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        SCALE_TYPE_FIT_XY -> {
                            binding.iv.scaleType = ImageView.ScaleType.FIT_XY
                            currentIvAttrScaleType = SCALE_TYPE_FIT_XY
                        }
                        SCALE_TYPE_FIT_START -> {
                            binding.iv.scaleType = ImageView.ScaleType.FIT_START
                            currentIvAttrScaleType = SCALE_TYPE_FIT_START
                        }
                        SCALE_TYPE_FIT_CENTER -> {
                            binding.iv.scaleType = ImageView.ScaleType.FIT_CENTER
                            currentIvAttrScaleType = SCALE_TYPE_FIT_CENTER
                        }
                        SCALE_TYPE_FIT_END -> {
                            binding.iv.scaleType = ImageView.ScaleType.FIT_END
                            currentIvAttrScaleType = SCALE_TYPE_FIT_END
                        }
                        SCALE_TYPE_CENTER -> {
                            binding.iv.scaleType = ImageView.ScaleType.CENTER
                            currentIvAttrScaleType = SCALE_TYPE_CENTER
                        }
                        SCALE_TYPE_CENTER_CROP -> {
                            binding.iv.scaleType = ImageView.ScaleType.CENTER_CROP
                            currentIvAttrScaleType = SCALE_TYPE_CENTER_CROP
                        }
                        SCALE_TYPE_CENTER_INSIDE -> {
                            binding.iv.scaleType = ImageView.ScaleType.CENTER_INSIDE
                            currentIvAttrScaleType = SCALE_TYPE_CENTER_INSIDE
                        }
                    }
                    info()
                }
                .build()
                .show()
    }

    private fun ivWhScale() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem(WH_SCALE_1_1)
                .addItem(WH_SCALE_9_16)
                .addItem(WH_SCALE_16_9)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    val layoutParams = binding.iv.layoutParams
                    when (tag) {
                        WH_SCALE_1_1 -> {
                            layoutParams.width = ScreenUtils.getScreenWidth()
                            layoutParams.height = ScreenUtils.getScreenWidth()
                            binding.iv.requestLayout()
                            currentIvWhScale = WH_SCALE_1_1
                        }
                        WH_SCALE_9_16 -> {
                            layoutParams.width = (ScreenUtils.getScreenWidth() * (9 / 16f)).toInt()
                            layoutParams.height = ScreenUtils.getScreenWidth()
                            binding.iv.requestLayout()
                            currentIvWhScale = WH_SCALE_9_16
                        }
                        WH_SCALE_16_9 -> {
                            layoutParams.width = ScreenUtils.getScreenWidth()
                            layoutParams.height = (ScreenUtils.getScreenWidth() * (9 / 16f)).toInt()
                            binding.iv.requestLayout()
                            currentIvWhScale = WH_SCALE_16_9
                        }
                    }
                    info()
                }
                .build()
                .show()
    }

    private fun picWhScale() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem(WH_SCALE_1_1)
                .addItem(WH_SCALE_9_16)
                .addItem(WH_SCALE_16_9)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    val layoutParams = binding.iv.layoutParams
                    when (tag) {
                        WH_SCALE_1_1 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_1_1)
                        }
                        WH_SCALE_9_16 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_9_16)
                        }
                        WH_SCALE_16_9 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_16_9)
                        }
                    }
                    info()
                }
                .build()
                .show()
    }

    @SuppressLint("SetTextI18n")
    private fun info() {
        val layoutParams = binding.iv.layoutParams
        val width = layoutParams.width
        val height = layoutParams.height

        val drawable = binding.iv.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        binding.tvInfo.text = "Iv控件wh：($width,$height), 比例：$currentIvWhScale\n" +
                "Iv的scaleType属性：$currentIvAttrScaleType\n" +
                "Pic的wh：(${bitmap?.width},${bitmap?.height})"
    }


}