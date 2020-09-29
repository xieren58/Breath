package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityIvScaleTypeBinding


class ImageViewScaleTypeActivity : BaseActivity() {

    companion object {
        const val IV_WH_SCALE_1_1 = "1:1"
        const val IV_WH_SCALE_9_16 = "9:16"
        const val IV_WH_SCALE_16_9 = "16:9"
    }

    private lateinit var binding: ActivityIvScaleTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIvScaleTypeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvScale.setOnClickListener(onClick)
    }

    private val onClick: ClickUtils.OnDebouncingClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            when (v) {
                binding.tvScale -> {
//                    scanMusic()
                    ivScale()
                    return
                }
            }
        }
    }

    private fun ivScale() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem(IV_WH_SCALE_1_1)
                .addItem(IV_WH_SCALE_9_16)
                .addItem(IV_WH_SCALE_16_9)
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        IV_WH_SCALE_1_1 -> {

                        }
                        IV_WH_SCALE_9_16 -> {

                        }
                        IV_WH_SCALE_16_9 -> {

                        }
                    }
                }
                .build()
                .show()
    }





}