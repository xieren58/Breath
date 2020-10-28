package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityIvAdjustViewBoundsBinding

/**
 * https://www.jianshu.com/p/49f8d5e5965b
 *
 *
 */
class ImageViewAdjustViewBoundsActivity : BaseActivity() {
    private lateinit var binding: ActivityIvAdjustViewBoundsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIvAdjustViewBoundsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    private val onClick: ClickUtils.OnDebouncingClickListener =
            object : ClickUtils.OnDebouncingClickListener() {
                override fun onDebouncingClick(v: View?) {
                    when (v) {
                    }
                }
            }

}