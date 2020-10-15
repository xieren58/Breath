package com.zkp.breath.component.activity.weight

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivitySwitchBinding

/**
 * https://blog.csdn.net/jiangtea/article/details/71189438
 * https://www.jianshu.com/p/796e2f7f0ed2
 *
 *
 * 原生的switch的缺点：
 * 1.layout_width和layout_height只能wrap_content，如果设置match_parent或者具体数据其实相当于设置
 *   父控件的大小（这时候可以看成还有一层父控件）
 * 2.只能通过switchMinWidth设置宽度
 * 3.不能单独调整圆点和椭圆的大小，样式等等
 */
class SwitchActivity : BaseActivity() {

    private lateinit var binding: ActivitySwitchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.switchCompat.setOnCheckedChangeListener { compoundButton, b ->

            binding.switchCompat.thumbTintList = ColorStateList.valueOf(
                    if (b) 0xff8844FF.toInt() else 0xffFAFAFA.toInt())

            binding.switchCompat.trackTintList = ColorStateList.valueOf(
                    if (b) 0x808844FF.toInt() else 0xffE8E8E8.toInt())
        }
    }


}