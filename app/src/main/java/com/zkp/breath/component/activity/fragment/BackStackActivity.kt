package com.zkp.breath.component.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.FragmentUtils
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentD
import com.zkp.breath.component.fragment.test.TestFragmentE
import com.zkp.breath.component.fragment.test.TestFragmentF
import com.zkp.breath.component.fragment.test.TestFragmentG
import kotlinx.android.synthetic.main.activity_fg_back_stack.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * https://juejin.cn/post/6844904090921779214#heading-2
 * https://juejin.cn/post/6844904086437904398
 */
class BackStackActivity : BaseActivity(R.layout.activity_fg_back_stack) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_action.setOnClickListener(onClickListener)
        tv_reordering_allowed.setOnClickListener(onClickListener)
        tv_remove.setOnClickListener(onClickListener)
    }

    private val onClickListener = object : ClickUtils.OnDebouncingClickListener() {
        override fun onDebouncingClick(v: View?) {
            v?.run {
                if (this == tv_action) {
                    action()
                    return
                }
                if (this == tv_reordering_allowed) {
                    reorderingAllowed()
                }
                if (this == tv_remove) {
                    removeDialog()
                }
            }
        }
    }

    private val mainScope by lazy {
        MainScope()
    }

    private fun removeDialog() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("add_addToBackStack")
                .addItem("remove_addToBackStack")
                .addItem("no_addToBackStack")
                .setOnSheetItemClickListener { dialog, itemView,
                                               position, tag ->
                    when (tag) {
                        "add_addToBackStack" -> {
                            remove(1)
                        }
                        "remove_addToBackStack" -> {
                            remove(2)
                        }
                        "no_addToBackStack" -> {
                            remove()
                        }
                    }
                }
                .build()
                .show()
    }

    /**
     * 打印生命周期流程查看下面几种情况的区别：
     *
     * 1. 在remove()和add()都没有执行addToBackStack()时，remove()会执行到fragment的onDetach()
     * 2. 只有add()执行addToBackStack()，因为add()存在返回栈事务，所以remove()只会执行fragment到
     *    onDestroyView()，在执行popBackStack()或者点击返回键会执行到onDetach()。
     * 3. 只有remove执行addToBackStack()，因为remove()存在返回栈事务，所以remove()会执行到fragment的
     *    onDestroyView()，在执行popBackStack()或者点击返回键会重新执行到onResume()。
     */
    private fun remove(flag: Int = 0) {
        actionPostion = 0
        FragmentUtils.removeAll(supportFragmentManager)

        mainScope.launch {
            val java = TestFragmentD::class.java
            val newInstance = java.newInstance()
            supportFragmentManager.commit {
                if (flag == 1) {
                    addToBackStack(java.name)
                }
                add(R.id.fcv, newInstance, java.name)
            }
            delay(2000)
            supportFragmentManager.commit {
                if (flag == 2) {
                    addToBackStack(java.name)
                }
                remove(newInstance)
            }
        }
    }

    private fun reorderingAllowed() {
        actionPostion = 0
        FragmentUtils.removeAll(supportFragmentManager)

        supportFragmentManager.commit {
            setCustomAnimations(R.anim.fragment_enter_anim,
                    R.anim.fragment_exit_anim)
            setReorderingAllowed(true)
            val java = TestFragmentD::class.java
            add(R.id.fcv, java.newInstance(), java.name)
            val javaE = TestFragmentE::class.java
            replace(R.id.fcv, javaE.newInstance(), javaE.name)
        }
    }

    private var actionPostion = 0
    private val data = mutableMapOf(
            0 to TestFragmentD(), 1 to TestFragmentE(), 2 to TestFragmentF(), 3 to TestFragmentG()
    )

    private fun action() {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("add")
                .addItem("hide")
                .addItem("popBackStack_flag0")
                .addItem("popBackStack_flag1")
                .addItem("findFragmentById")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "add" -> {
                            val value = data[actionPostion]
                            value?.run {
                                supportFragmentManager.commit {
                                    val name = value.javaClass.name
                                    addToBackStack(name)
                                    add(R.id.fcv, value, name)
                                }
                                actionPostion++
                            }
                        }
                        "hide" -> {
                            val topShow = FragmentUtils.getTopShow(supportFragmentManager)
                            topShow?.run {
                                supportFragmentManager.commit {
                                    setReorderingAllowed(true)
                                    addToBackStack(topShow.javaClass.name)
                                    hide(topShow)
                                }
                            }
                        }
                        "popBackStack_flag0" -> {
                            val baseFragment = data[0]
                            baseFragment?.run {
                                supportFragmentManager.popBackStack(javaClass.name, 0)
                                actionPostion = 1
                            }
                        }
                        "popBackStack_flag1" -> {
                            val baseFragment = data[0]
                            baseFragment?.run {
                                supportFragmentManager.popBackStack(javaClass.name,
                                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                                actionPostion = 0
                            }
                        }
                        "findFragmentById" -> {
                            val findFragmentById = supportFragmentManager.findFragmentById(R.id.fcv)
                            findFragmentById?.run {
                                Log.i("findFragmentById获取", "fragment: $findFragmentById")
                            }

                            if (actionPostion >= 1) {
                                val baseFragment = data[actionPostion - 1]
                                baseFragment?.run {
                                    supportFragmentManager.findFragmentByTag(baseFragment.javaClass.name)
                                    Log.i("findFragmentByTag获取", "fragment: $baseFragment")
                                }
                            }

                        }
                    }
                }
                .build()
                .show()
    }

}