package com.zkp.breath.component.activity.weight

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Group
import androidx.constraintlayout.widget.Placeholder
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import com.zkp.breath.R
import com.zkp.breath.adpter.ConstraintVpAdapter
import com.zkp.breath.bean.ConstraintFunctionBean
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.zkplib.anim.ObjectAnimatorAssist
import kotlinx.android.synthetic.main.activity_constraint_layout.*
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * 约束布局的demo
 *
 *
 * https://github.com/google/flexbox-layout
 *
 * https://juejin.cn/post/6844904199004618765
 * https://juejin.cn/post/6854573221312725000
 * https://juejin.cn/post/6844903872255754248
 * https://blog.csdn.net/weixin_34677811/article/details/90719945
 */
class ConstraintLayoutActivity : BaseActivity(R.layout.activity_constraint_layout) {

    private lateinit var constraintVpAdapter: ConstraintVpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        val mutableListOf: MutableList<ConstraintFunctionBean> = mutableListOf()
        mutableListOf.run {
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.base_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.flow_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.layer_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.iv_filter_btn_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.mock_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.space_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.flow_api_function))
            mutableListOf.add(ConstraintFunctionBean(ConstraintFunctionBean.group_function))
        }

        // 设置方向
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        constraintVpAdapter = ConstraintVpAdapter(mutableListOf)
        constraintVpAdapter.addChildClickViewIds(
                R.id.layer,
                R.id.tv_flow_api,
                R.id.btn_wrap_mode,
                R.id.btn_horizontal_style,
                R.id.btn_vertical_style,
                R.id.btn_horizontal_align,
                R.id.btn_vertical_align,
                R.id.btn_group1,
                R.id.btn_group2,
                R.id.btn_placeholder
        )
        constraintVpAdapter.setOnItemChildClickListener(onItemChildClickListener)
        viewpager2.adapter = constraintVpAdapter
    }

    private val onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
        if (R.id.layer == view.id) {
            layerAnimator(view)
            return@OnItemChildClickListener
        }

        if (R.id.tv_flow_api == view.id) {
            constraintProperties(view)
            return@OnItemChildClickListener
        }

        if (R.id.btn_wrap_mode == view.id) {
            val flow = constraintVpAdapter.getViewByPosition(position, R.id.flow) as Flow
            flowWrapMode(flow)
            return@OnItemChildClickListener
        }

        if (R.id.btn_horizontal_style == view.id) {
            val flow = constraintVpAdapter.getViewByPosition(position, R.id.flow) as Flow
            flowHorizontalStyle(flow)
            return@OnItemChildClickListener
        }

        if (R.id.btn_vertical_style == view.id) {
            val flow = constraintVpAdapter.getViewByPosition(position, R.id.flow) as Flow
            flowVerticalStyle(flow)
            return@OnItemChildClickListener
        }

        if (R.id.btn_horizontal_align == view.id) {
            val flow = constraintVpAdapter.getViewByPosition(position, R.id.flow) as Flow
            flowHorizontalAlign(flow)
            return@OnItemChildClickListener
        }

        if (R.id.btn_vertical_align == view.id) {
            val flow = constraintVpAdapter.getViewByPosition(position, R.id.flow) as Flow
            flowVerticalAlign(flow)
            return@OnItemChildClickListener
        }

        if (R.id.btn_group1 == view.id) {
            val group = constraintVpAdapter.getViewByPosition(position, R.id.group1) as Group
            group.visibility = if (group.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            return@OnItemChildClickListener
        }

        if (R.id.btn_group2 == view.id) {
            val group1 = constraintVpAdapter.getViewByPosition(position, R.id.group1) as Group
            val group2 = constraintVpAdapter.getViewByPosition(position, R.id.group2) as Group
            group2.visibility = if (group1.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            return@OnItemChildClickListener
        }

        if (R.id.btn_placeholder == view.id) {
            val placeholder = constraintVpAdapter.getViewByPosition(position, R.id.place_holder) as Placeholder
            placeholder.setContentId(R.id.btn_placeholder)
        }
    }

    private fun flowVerticalAlign(flow: Flow) {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("top")
                .addItem("bottom")
                .addItem("center")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "top" -> {
                            flow.setVerticalAlign(Flow.HORIZONTAL_ALIGN_START)
                        }
                        "bottom" -> {
                            flow.setVerticalAlign(Flow.HORIZONTAL_ALIGN_END)
                        }
                        "center" -> {
                            flow.setVerticalAlign(Flow.HORIZONTAL_ALIGN_CENTER)
                        }
                    }
                }
                .build()
                .show()
    }

    private fun flowHorizontalAlign(flow: Flow) {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("start")
                .addItem("end")
                .addItem("center")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "start" -> {
                            flow.setHorizontalAlign(Flow.HORIZONTAL_ALIGN_START)
                        }
                        "end" -> {
                            flow.setHorizontalAlign(Flow.HORIZONTAL_ALIGN_END)
                        }
                        "center" -> {
                            flow.setHorizontalAlign(Flow.HORIZONTAL_ALIGN_CENTER)
                        }
                    }
                }
                .build()
                .show()
    }

    private fun flowVerticalStyle(flow: Flow) {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("packed")
                .addItem("spread")
                .addItem("spread_inside")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "packed" -> {
                            flow.setVerticalStyle(Flow.CHAIN_PACKED)
                        }
                        "spread" -> {
                            flow.setVerticalStyle(Flow.CHAIN_SPREAD)
                        }
                        "spread_inside" -> {
                            flow.setVerticalStyle(Flow.CHAIN_SPREAD_INSIDE)
                        }
                    }
                }
                .build()
                .show()
    }

    private fun flowHorizontalStyle(flow: Flow) {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("packed")
                .addItem("spread")
                .addItem("spread_inside")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "packed" -> {
                            flow.setHorizontalStyle(Flow.CHAIN_PACKED)
                        }
                        "spread" -> {
                            flow.setHorizontalStyle(Flow.CHAIN_SPREAD)
                        }
                        "spread_inside" -> {
                            flow.setHorizontalStyle(Flow.CHAIN_SPREAD_INSIDE)
                        }
                    }
                }
                .build()
                .show()
    }

    private fun flowWrapMode(flow: Flow) {
        QMUIBottomSheet.BottomListSheetBuilder(this)
                .setGravityCenter(true)
                .addItem("none")
                .addItem("chain")
                .addItem("aligned")
                .setOnSheetItemClickListener { dialog, itemView, position, tag ->
                    when (tag) {
                        "none" -> {
                            flow.setWrapMode(Flow.WRAP_NONE)
                        }
                        "chain" -> {
                            flow.setWrapMode(Flow.WRAP_CHAIN)
                        }
                        "aligned" -> {
                            flow.setWrapMode(Flow.WRAP_ALIGNED)
                        }
                    }
                }
                .build()
                .show()
    }

    /**
     * 2.0以后，对属性的修改提供了流式API。
     *
     * 好像很多属性都没效果！！！！
     */
    private fun constraintProperties(view: View) {
        ConstraintProperties(view)
                .alpha(0.5f)
                .constrainWidth(AutoSizeUtils.dp2px(view.context, 200f))    // 无效果
                .constrainHeight(AutoSizeUtils.dp2px(view.context, 200f))   // 无效果
                .margin(ConstraintSet.TOP, AutoSizeUtils.dp2px(view.context, 100f)) // 无效果
                .apply()
    }

    private lateinit var layerAnim: ObjectAnimator
    private fun layerAnimator(view: View) {
        if (::layerAnim.isInitialized) {
            layerAnim.start()
            return
        }
        layerAnim = ObjectAnimatorAssist.Builder()
                .setView(view)
                .setDuration(2000)
                .setAnimatorType(ObjectAnimatorAssist.ObjectAnimatorType.ROTATION)
                .setStartValue(0f)
                .setEndValue(360f)
                .setRepeatCount(2)
                .build()
                .startAnimator()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::layerAnim.isInitialized) {
            layerAnim.cancel()
        }
    }

}