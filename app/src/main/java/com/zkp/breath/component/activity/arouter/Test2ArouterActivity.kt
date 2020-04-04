package com.zkp.breath.component.activity.arouter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.bean.ArouterParamsBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAruoterTestBinding

/**
 * 携带参数跳转例子
 * 注意：kotlin的字段必须加上JvmField注解，否则运行会报错：ARouter::Compiler An exception is encountered, [The inject fields CAN NOT BE 'private'!!!
 */
@Route(path = TEST2_AROUTER_ACTIVITY_PATH)
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
        Log.i(TAG, "toString(): ${toString()}");
    }

    override fun toString(): String {
        return "Test2ArouterActivity(key1='$key1', key2=$key2, key3=$key3, key4=$key4)"
    }


}