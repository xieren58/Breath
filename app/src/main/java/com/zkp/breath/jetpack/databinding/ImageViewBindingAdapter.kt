package com.zkp.breath.jetpack.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zkp.breath.R

/**
 * 补全自定义的属性，自定义了一个BindingAdapter。（可参考TextViewBindingAdapter，xml中对控件的属性进行赋值数据
 * 其实是调用了"布局文件名 + Binding + Impl"这个类，而这个类内部其实就是调用了这些"XXX + BindingAdapter"的工具
 * 方法进行赋值操作，而这些方法都有进行判空操作。）
 *
 * 注意：
 * 1.需要在attrs.xml添加自定义属性。
 * 2.DataBinding的@BindingAdapter也支持同时指定多个属性（value是个可变参数，即数组）。默认来说，当指定多个属性时，
 * 这些属性要同时地使用到同一个控件上，有点像控件中强制要求设定属性layout_width和layout_height一样，否则的话会报错。
 * 我们也可以通过设置requireAll的值来指定是否强制所有的属性同时使用。
 */
class ImageViewBindingAdapter {

    @BindingAdapter("url", requireAll = false)
    fun bindImgUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).error(R.drawable.bg_rcv_item).into(imageView)
    }

}