package com.zkp.breath.weight

import android.content.Context
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class EnterConstraintHelper constructor(
        context: Context) : ConstraintHelper(context) {

//
//    /**
//     * 更新约束
//     */
//    override fun updatePostConstraints(constainer: ConstraintLayout?) {
//        super.updatePostConstraints(constainer)
//    }
//
//    /**
//     * 测量后更新
//     */
//    override fun updatePostMeasure(container: ConstraintLayout?) {
//        super.updatePostMeasure(container)
//    }
//
//    /**
//     * 布局前更新
//     */
//    override fun updatePreLayout(container: ConstraintLayout?) {
//        super.updatePreLayout(container)
//    }
//
//    /**
//     * 布局后更新
//     */
//    override fun updatePreLayout(container: ConstraintWidgetContainer?, helper: Helper?,
//                                 map: SparseArray<ConstraintWidget>?) {
//        super.updatePreLayout(container, helper, map)
//    }
//
//    override fun updatePostLayout(container: ConstraintLayout?) {
//        super.updatePostLayout(container)
//    }
//
//    /**
//     * 绘制前更新
//     */
//    override fun updatePreDraw(container: ConstraintLayout?) {
//        super.updatePreDraw(container)
//    }


    var firstLoad = true
    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (!firstLoad) {
            return
        }
        if (this.mReferenceIds != null) {
            this.setIds(this.mReferenceIds)
        }
        val views = getViews(container)
        for (view in views) {
            val anim = ViewAnimationUtils.createCircularReveal(
                    view, view.width / 2,
                    view.height / 2, 0f,
                    Math.hypot((view.height / 2).toDouble(),
                            (view.width / 2).toDouble()).toFloat())
            anim.duration = 3000
            anim.start()
        }
        firstLoad = false
    }


}