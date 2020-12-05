package com.zkp.breath.component.activity

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentA
import com.zkp.breath.component.fragment.test.TestFragmentB
import com.zkp.breath.component.fragment.test.TestFragmentC
import com.zkp.breath.component.fragment.test.TestFragmentD
import com.zkp.breath.databinding.ActivityFragmentBinding

/**
 * https://juejin.cn/post/6900739309826441224
 */
class FragmentDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding
    private var fragmentsList: ArrayList<Fragment> = ArrayList()
    private var fragmentsTagList: Array<String?> = arrayOfNulls(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)
    }

    fun initView(savedInstanceState: Bundle?) {

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
//        FragmentUtils.add(supportFragmentManager, fragmentsList, R.id.fcv, fragmentsTagList, 0)


        /**
         * 仅当 saveInstanceState 为 null 时才创建 fragment 事务。这是为了确保当 activity 第一次 create
         * 时 fragment 仅被添加一次。当系统资源回收或配置发生变化时 saveInstanceState 不为 null 无需再次添加该
         * fragment，因为 fragment 会自动从 savedInstanceState 中恢复。
         */
        if (savedInstanceState == null) {
            // 参数
            val bundle = bundleOf("some_int" to 0)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fcv, TestFragmentA::class.java, bundle)
            }
        }


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
//
//            FragmentUtils.add(supportFragmentManager, testFragmentD, R.id.fcv, false)
//            fragmentsList.remove(testFragmentC)    // 集合中要删除，否则会出现内存泄露
//            FragmentUtils.remove(testFragmentC)
//
//            // 不要使用FragmentUtils.replace，如果不先把fragment的list清除则会内存泄漏
////            fragmentsList.clear()
////            FragmentUtils.replace(testFragmentC, testFragmentD, testFragmentD.javaClass.simpleName)
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