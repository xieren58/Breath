package com.zkp.breath.component.fragment.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Fragment支持构造函数传入布局，内部自动调用onCreateView（）函数。
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    val TAG = this::class.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(tag, "onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag, "onCreate()")
    }

    /**
     * 该方法返回Fragment的UI布局，使用getView()获取跟布局的root_view。
     *
     * 1.重写后使用ViewBinding的方式传入布局。
     * 2.如果使用主构造函数传入布局则不需要重写该方法。
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(tag, "onCreateView()")
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        val viewBinding = viewBinding(inflater, container, false)
        if (onCreateView == null && viewBinding == null) {
            throw IllegalStateException("请调用构造函数传入LayoutRes或者有效调用viewBinding()返回所属Fragment的布局")
        }
        if (onCreateView != null && viewBinding != null) {
            // viewbinding创建方式优先
            return viewBinding
        }
        if (onCreateView != null && viewBinding == null) {
            return onCreateView
        }

        if (viewBinding != null && onCreateView == null) {
            return viewBinding
        }
        return null
    }

    /**
     * frgment使用viewbinding创建布局和Fragment的onCreateView一样，都需要传入三个参数，且最后一个参数默认为false。
     * 如果不这样创建不能占满布局，详情可以看LayoutInflater.inflate()方法。
     */
    abstract fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean = false): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tag, "onViewCreated()")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(tag, "onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(tag, "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(tag, "onDetach()")
    }


}