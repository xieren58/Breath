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


/**
 * https://www.jianshu.com/p/c0bfa575d163
 *
 * ImageView的ScaleType:
 * FIT_XY：不按图片原比例伸缩，强制让图片充满ImageVie，图片可以完整显示但可能会变形。
 * FIT_CENTER，FIT_END，FIT_START：按照图片原比例伸缩，直到图片最长边和ImageView对应的宽或高重叠，
 *                      能够保证图片完整显示。因为要"保证图片能够完整显示"，所以只要操作最长边就能实现。
 * CENTER：不进行任何伸缩，图片中点和ImageView重叠，按照ImageView的宽高裁剪图片，保证图片居中显示，不保证图片能够完整显示或者填满控件。
 * CENTER_CROP：按图片比例以可能裁切掉部分图片为代价，让图片充满ImageView。因为要"保证图片充满控件"，所以只要操作最短边就能实现。
 * CENTER_INSIDE：当原图任意一边长度大于ImageView的对应边时，相当于FIT_CENTER。当原图两边长度都小于等于ImageView的时候，相当于CENTER。
 *
 */
class ImageViewScaleTypeActivity : BaseActivity() {

    companion object {
        const val WH_SCALE_1_1 = "1:1"
        const val WH_SCALE_9_16 = "9:16"
        const val WH_SCALE_16_9 = "16:9"
        const val WH_SCALE_4_3 = "4:3"

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
    private var currentPicWhScale = ""
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
                .addItem(WH_SCALE_4_3)
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
                        WH_SCALE_4_3 -> {
                            layoutParams.width = ScreenUtils.getScreenWidth()
                            layoutParams.height = (ScreenUtils.getScreenWidth() * (3 / 4f)).toInt()
                            binding.iv.requestLayout()
                            currentIvWhScale = WH_SCALE_4_3
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
                .addItem(WH_SCALE_4_3)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    val layoutParams = binding.iv.layoutParams
                    when (tag) {
                        WH_SCALE_1_1 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_1_1)
                            currentPicWhScale = WH_SCALE_1_1
                        }
                        WH_SCALE_9_16 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_9_16)
                            currentPicWhScale = WH_SCALE_9_16
                        }
                        WH_SCALE_16_9 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_16_9)
                            currentPicWhScale = WH_SCALE_16_9
                        }
                        WH_SCALE_4_3 -> {
                            binding.iv.setImageResource(R.drawable.ic_wh_4_3)
                            currentPicWhScale = WH_SCALE_4_3
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

        var str = ""

        when (currentIvAttrScaleType) {
            SCALE_TYPE_FIT_XY -> {
                str = "不按图片原比例伸缩，强制让图片充满ImageVie，图片可以完整显示但可能会变形。"
            }
            SCALE_TYPE_FIT_START -> {
                str = "按照图片原比例伸缩，直到图片最长边和ImageView对应的宽或高重叠，能够保证图片完整显示。因为要\"保证图片能够完整显示\"，所以只要操作最长边就能实现。"
            }
            SCALE_TYPE_FIT_CENTER -> {
                str = "按照图片原比例伸缩，直到图片最长边和ImageView对应的宽或高重叠，能够保证图片完整显示。因为要\"保证图片能够完整显示\"，所以只要操作最长边就能实现。"
            }
            SCALE_TYPE_FIT_END -> {
                str = "按照图片原比例伸缩，直到图片最长边和ImageView对应的宽或高重叠，能够保证图片完整显示。因为要\"保证图片能够完整显示\"，所以只要操作最长边就能实现。"
            }
            SCALE_TYPE_CENTER -> {
                str = "不进行任何伸缩，图片中点和ImageView重叠，按照ImageView的宽高裁剪图片，保证图片居中显示，不保证图片能够完整显示或者填满控件。"
            }
            SCALE_TYPE_CENTER_CROP -> {
                str = "按图片比例以可能裁切掉部分图片为代价，让图片充满ImageView。因为要\"保证图片充满控件\"，所以只要操作最短边就能实现。"
            }
            SCALE_TYPE_CENTER_INSIDE -> {
                str = "CENTER_INSIDE：当原图任意一边长度大于ImageView的对应边时，相当于FIT_CENTER。当原图两边长度都小于等于ImageView的时候，相当于CENTER。"
            }
        }

        binding.tvInfo.text = "$currentIvAttrScaleType：$str\n\n" +
                "Iv的scaleType属性：$currentIvAttrScaleType\n" +
                "Iv控件wh：($width,$height), 比例：$currentIvWhScale\n" +
                "Pic的wh：(${bitmap?.width},${bitmap?.height}), 比例：$currentPicWhScale\n"


    }


}