package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zkp.breath.R
import com.zkp.breath.adpter.EntranceAdapter
import com.zkp.breath.adpter.decoration.EntranceItemDecoration
import com.zkp.breath.component.activity.EventActivity
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.activity.jetpack.*
import com.zkp.breath.component.activity.weight.*
import kotlinx.android.synthetic.main.activity_entrance.*

class WeightActivity : BaseActivity(R.layout.activity_entrance) {

    private val listOf = mutableListOf(
            "ViewEvent",
            "Coordinator", "Svga", "Lottie", "ScrollView", "ConstraintLayout",
            "TabLayout", "TextView", "Button", "Switch", "ImageViewAdjustViewBounds",
            "ImageViewScaleType", "MotionLayout", "EditText", "QMUI"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clt.setBackgroundColor(ColorUtils.getColor(R.color.colorFFFFEB3B))
        initRcv()
    }

    private fun initRcv() {
        rcv.addItemDecoration(EntranceItemDecoration())
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val gridLayoutManager = GridLayoutManager(rcv.context, 2)
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = EntranceAdapter(listOf)
        gridAdapter.setOnItemClickListener(onItemChildClickListener)
        rcv.adapter = gridAdapter
    }

    private val onItemChildClickListener = OnItemClickListener { _, _, position ->
        when (val type = listOf[position]) {
            "ViewEvent" -> {
                ActivityUtils.startActivity(EventActivity::class.java)
                return@OnItemClickListener
            }
            "Coordinator" -> {
                ActivityUtils.startActivity(CoordinatorActivity::class.java)
                return@OnItemClickListener
            }
            "Svga" -> {
                ActivityUtils.startActivity(SvgaActivity::class.java)
                return@OnItemClickListener
            }
            "Lottie" -> {
                ActivityUtils.startActivity(LottieActivity::class.java)
                return@OnItemClickListener
            }
            "ScrollView" -> {
                ActivityUtils.startActivity(ScrollViewActivity::class.java)
                return@OnItemClickListener
            }
            "ConstraintLayout" -> {
                ActivityUtils.startActivity(ConstraintLayoutActivity::class.java)
                return@OnItemClickListener
            }
            "TabLayout" -> {
                ActivityUtils.startActivity(TabLayoutActivity::class.java)
                return@OnItemClickListener
            }
            "TextView" -> {
                ActivityUtils.startActivity(TextViewActivity::class.java)
                return@OnItemClickListener
            }
            "Button" -> {
                ActivityUtils.startActivity(ButtonActivity::class.java)
                return@OnItemClickListener
            }
            "Switch" -> {
                ActivityUtils.startActivity(SwitchActivity::class.java)
                return@OnItemClickListener
            }
            "ImageViewAdjustViewBounds" -> {
                ActivityUtils.startActivity(ImageViewAdjustViewBoundsActivity::class.java)
                return@OnItemClickListener
            }
            "ImageViewScaleType" -> {
                ActivityUtils.startActivity(ImageViewScaleTypeActivity::class.java)
                return@OnItemClickListener
            }
            "MotionLayout" -> {
                ActivityUtils.startActivity(MotionLayoutActivity::class.java)
                return@OnItemClickListener
            }
            "EditText" -> {
                ActivityUtils.startActivity(EditTextActivity::class.java)
                return@OnItemClickListener
            }
            "QMUI" -> {
                ActivityUtils.startActivity(QMUIActivity::class.java)
                return@OnItemClickListener
            }
            else -> {
                ToastUtils.showShort(type)
            }
        }
    }

}