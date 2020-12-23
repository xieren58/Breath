package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.*
import com.zkp.breath.BR
import com.zkp.breath.R
import com.zkp.breath.databinding.ActivityDatabindingBinding
import com.zkp.breath.jetpack.databinding.DataBindingViewModel


/**
 *
 * BindingAdapter：
 * 以xml中TextView的android:text="@{user.firstName}为例，DataBinding 中使用 Binding Adapter 来处理，
 * 它主要处理「属性」和「事件」。详情查看TextViewBindingAdapter，DataBinding为我们提供了很多控件的BindingAdapter，
 * 如果不满足还可以自定义BindingAdapter。
 *
 *
 *  DataBinding优点：
 *  1. 具有ViewBinding的优点。
 *  2. 双向绑定：当数据改变时同时使视图刷新，而视图改变时也可以同时改变数据。
 *
 * 缺点：
 *      1.BaseObservable的不像ViewModel的实现类是具备数据持久化的能力
 *      2.BaseObservable的成员变量不具备LiveData感知组件的生命周期的能力
 *      3.处理ImageView的加载图片，需要自定义属性后自定义BindingAdapter进行处理。（无法使用import glide进入xml中，
 *        因为需要上下文，而xml没有上下文可以获取）
 *      4.一定程度上是将操作移入了xml中，导致xml的可读性会下降，职责不单一，代码量上涨(导包)。（这就有点拆东墙补西墙的感觉）
 *
 * https://juejin.im/post/6844903609079971854
 * https://juejin.cn/post/6844904119388340238
 */
class DataBindingActivity : AppCompatActivity() {

    val tag = this::class.java.simpleName + "_Tag"

    private lateinit var binding: ActivityDatabindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        val dataBindingViewModel = DataBindingViewModel()
        /**
         * 实现了 Observable 接口的类允许注册一个监听器，当可观察对象的属性更改时就会通知这个监听器，
         * 此时就需要用到 OnPropertyChangedCallback
         */
        dataBindingViewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.i("是否改变", "onPropertyChanged: ")
                // 参数1就是调用注册方法的对象
                when (propertyId) {
                    BR.num -> {
                        Log.i(tag, "BR.num")
                    }
//                    BR.url -> {
//                        Log.i(tag, "BR.url")
//                    }
                }
            }
        })
        binding.vm = dataBindingViewModel
        binding.vmStr = "我们"

        // 相当于viewbinding的用法
        binding.tvPlainUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // 测试空安全
                val plainUser = PlainUser()
                plainUser.lastName.set("我是最后的LastName")
                binding.plainUser = plainUser
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    /**
     * 一种更细粒度的绑定方式，可以具体到成员变量，这种方式无需继承 BaseObservable，一个简单的 POJO 就可以实现。
     * 其实内部帮我们自己继承了BaseObservable
     */
    class PlainUser {
        var list: ObservableList<String> = ObservableArrayList()
        val map: ObservableMap<String, String> = ObservableArrayMap()
        val lastName = ObservableField<String>()
        val age = ObservableInt()
    }
}