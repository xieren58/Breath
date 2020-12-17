package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityViewbindingBinding

/**
 * https://mp.weixin.qq.com/s/BSdzKSOiAWG08epvXN5q2w
 * https://juejin.cn/post/6905942568467759111
 *
 * ViewBinding优势：
 * 1.防止类型转换异常，修改了控件类型马上进行错误提示。
 * 2.防止空异常。（一般的获取操作一定是非空）
 *
 * kotlin-android-extensions插件(在 2021 年 9 月或之后的 Kotlin 版本中将被移除):
 * 1.使用 Kotlin 合成方法（Synthetic 视图）取代 findViewById，通过引入 kotlinx.android.synthetic 可以直接
 *   使用控件的 ID。相同布局文件存在相同id，导入不对应的布局，没有针对 ID 进行无效检查，编译和运行都不会报错，但这
 *   样并不安全。
 * 2.手动实现 Parcelize 比较麻烦，所以 Kotlin 提供了 @Parcelize 注解帮助快速实现 Parcelize
 *
 */
class ViewBindingActivity : BaseActivity() {

    private lateinit var binding: ActivityViewbindingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         LayoutInflater.from(this)或者getLayoutInflater()
        binding = ActivityViewbindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
//         调用include内部控件的方式：binding + include布局id + 控件id
        binding.appbar.tvTitle.text = "ViewBindingActivity"
    }

}