package com.zkp.breath

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.umeng.analytics.MobclickAgent
import com.zkp.breath.component.activity.FragmentDemoActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.mvx.MvvmActivity
import com.zkp.breath.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 模拟UM的事件统计
        val umEventMap: MutableMap<String, Any> = HashMap()
        umEventMap["name"] = "zkp"
        umEventMap["page_name"] = "main_activity"
        MobclickAgent.onEventObject(this, "um_main_event", umEventMap)

        ActivityUtils.startActivity(MvvmActivity::class.java)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //  保存点击的时间
    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                System.exit(0)
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}