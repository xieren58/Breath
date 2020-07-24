package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.arouter.ActivityRouterPath
import com.zkp.breath.arouter.entity.ArouterParamsBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterTestBinding

/**
 * 携带参数跳转例子
 * 注意：kotlin的字段必须加上JvmField注解，否则运行会报错：ARouter::Compiler An exception is encountered, [The inject fields CAN NOT BE 'private'!!!
 *
 * 我们经常需要在目标页面中配置一些属性，比方说"是否需要登陆"之类的
 *可以通过 Route 注解中的 extras 属性进行扩展，这个属性是一个 int值，换句话说，单个int有4字节，也就是32位，可以配置32个开关
 *剩下的可以自行发挥，通过字节操作可以标识32个开关，通过开关标记目标页面的一些属性，在拦截器中可以拿到这个标记进行业务逻辑判断
 *
 */
@Route(path = ActivityRouterPath.TEST2_AROUTER_ACTIVITY_PATH, extras = 0x00000011)
class Test2ArouterActivity : BaseActivity() {

    lateinit var binding: ActivityAruoterTestBinding

    @JvmField
    @Autowired
    var key1: String = ""

    @JvmField
    @Autowired
    var key2: Int = 0

    @JvmField
    @Autowired
    var key3: ArouterParamsBean = ArouterParamsBean()

    @JvmField
    @Autowired
    var key4: ArrayList<Int> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAruoterTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        ToastUtils.showShort("Test2ArouterActivity")
        Log.i(ACTIVITY_TAG, "toString(): ${toString()}");
    }

    override fun toString(): String {
        return "Test2ArouterActivity(key1='$key1', key2=$key2, key3=$key3, key4=$key4)"
    }


}