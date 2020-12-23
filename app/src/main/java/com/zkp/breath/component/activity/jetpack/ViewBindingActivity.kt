package com.zkp.breath.component.activity.jetpack

import android.annotation.SuppressLint
import android.os.Bundle
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityViewbindingBinding

/**
 * https://mp.weixin.qq.com/s/BSdzKSOiAWG08epvXN5q2w
 * https://juejin.cn/post/6905942568467759111
 *
 * 魔法背后：只要开启DataBinding或者ViewBinding会对每个布局xml生成"布局文件名 + Binding"类。
 * 存放目录：build -> generated -> data_binding_base_class_source_out 目录。
 *
 * ViewBinding优势：
 * 1.防止类型安全问题。（binding类的控件成员变量类型是固定的，也会随着xml的变化而变化，不像butterknife不会自动同步xml修改）
 * 2.防止空异常。（binding类的控件成员变量都是NonNull注解，如果存在不同配置的不同布局文件（如横竖屏）且该控件不
 *              是存在于所有布局，该处使用 Nullable注解标记）
 * 3.替换findViewById，减少模板代码（binding类内部其实还是使用findViewById）。
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
        /**
         * LayoutInflater.from(this)或者getLayoutInflater()获取LayoutInflater实例
         *
         */
        binding = ActivityViewbindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * 调用include内部控件的方式：binding + include布局id + 控件id
         */
        binding.appbar.tvTitle.text = "ViewBindingActivity"
    }

}