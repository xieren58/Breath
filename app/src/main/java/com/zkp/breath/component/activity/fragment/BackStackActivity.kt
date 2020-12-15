package com.zkp.breath.component.activity.fragment

import android.os.Bundle
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

/**
 * https://juejin.cn/post/6844904090921779214#heading-2
 * https://juejin.cn/post/6844904086437904398
 */
class BackStackActivity : BaseActivity(R.layout.activity_fg_back_stack) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_action.setOnClickListener(onClickListener)
        tv_reordering_allowed.setOnClickListener(onClickListener)
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
                    }
                }
                .build()
                .show()
    }

}