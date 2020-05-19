package com.zkp.breath.component.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentA
import com.zkp.breath.component.fragment.test.TestFragmentB
import com.zkp.breath.component.fragment.test.TestFragmentC
import com.zkp.breath.component.fragment.test.TestFragmentD
import com.zkp.breath.databinding.ActivityFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding
    private var fragmentsList: ArrayList<Fragment> = ArrayList()
    private var fragmentsTagList: Array<String?> = arrayOfNulls(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    fun initView() {

        val testFragmentA = TestFragmentA()
        fragmentsList.add(testFragmentA)
        fragmentsTagList[0] = testFragmentA.javaClass.simpleName

        val testFragmentB = TestFragmentB()
        fragmentsList.add(testFragmentB)
        fragmentsTagList[1] = testFragmentB.javaClass.simpleName

        val testFragmentC = TestFragmentC()
        fragmentsList.add(testFragmentC)
        fragmentsTagList[2] = testFragmentC.javaClass.simpleName

        val testFragmentD = TestFragmentD()

        // 批量导入显示角标为0的fg
        FragmentUtils.add(supportFragmentManager, fragmentsList, R.id.clt_root, fragmentsTagList, 0)

//        GlobalScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//            }
//
//            // 显示隐藏
//            ToastUtils.showShort("show B")
//            FragmentUtils.hide(testFragmentA)
//            FragmentUtils.show(testFragmentB)
//
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//            }
//
//            // 显示隐藏
//            ToastUtils.showShort("show C")
//            FragmentUtils.showHide(testFragmentC, testFragmentB)
//
//            // 替换
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//            }
//            FragmentUtils.replace(testFragmentC, testFragmentD, testFragmentD.javaClass.simpleName)
//
//            val top = FragmentUtils.getTop(supportFragmentManager)
//            val topInStack = FragmentUtils.getTopInStack(supportFragmentManager)
//
//            val topShow = FragmentUtils.getTopShow(supportFragmentManager)
//            val topShowInStack = FragmentUtils.getTopShowInStack(supportFragmentManager)
//
//            val fragments = FragmentUtils.getFragments(supportFragmentManager)
//            val fragmentsInStack = FragmentUtils.getFragmentsInStack(supportFragmentManager)
//
//            val allFragments = FragmentUtils.getAllFragments(supportFragmentManager)
//            val allFragmentsInStack = FragmentUtils.getAllFragmentsInStack(supportFragmentManager)
//
//            val findFragment = FragmentUtils.findFragment(supportFragmentManager, testFragmentC.javaClass.simpleName)
//
////            FragmentUtils.pop(supportFragmentManager)
////            FragmentUtils.popAll(supportFragmentManager)
//
////            FragmentUtils.remove(testFragmentC)
////            FragmentUtils.removeAll(supportFragmentManager)
//
//        }

    }

}