package com.zkp.breath.adpter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.CollectionUtils

/**
 * FragmentPagerAdapter和FragmentStatePagerAdapter的区别：instantiateItem(ViewGroup, int)和destroyItem
 *      (ViewGroup, int, Object)的实现方式不一样。（可以通过打印生命周期方法判断）
 *
 * 1. FragmentPagerAdapter：该类内的每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也
 *    比较少的那种。
 *
 *   > 内存不可回收：limit外的Fragment不会被回收，所以Fragment占用的内存也就不会被回收，好处是复用。instantiateItem()
 *        方法中创建的Fragment都存放在外部传入的FragmentManager中；destroyItem()方法中不会移除Fragment，只是
 *        会将在onCreateView创建的视图与这个fragment分离（打印生命周期最多可见onDestroyView）。
 *
 *   > 通过设置新的Adapter刷新是无效的，因为instantiateItem()内部getItemId()返回的是数据的下标。我们可以选择
 *        重写getItemId（）然后返回hashCode就能实现。
 *
 * 2. FragmentStatePagerAdapter: 适合需要处理有很多页，并且数据动态性较大、占用内存较多的情况。
 *
 *   > 内存可回收：将会对limit外的Fragment进行回收，所以Fragment占用的内存也会被回收。instantiateItem()创建
 *         的Fragment会存放在集合中；destroyItem()会移除Fragment且从集合中移除，在销毁Fragment时会调用Fragment
 *         的onSaveInstanceState方法保存一些数据信息，然后存放在内部的集合中，当下一次创建Fragment时会将这些数据
 *         读取出来，然后通过Fragment的onCreate等方法回传。（打印生命周期最多可见onDetach）
 *
 *   > 通过设置新的Adapter刷新是有效的，因为存放在集合中的Fragment会被清空，所以一定会重建Fragment。
 *
 *
 * behavior参数详解：（可以通过打印生命周期方法判断）
 * BEHAVIOR_SET_USER_VISIBLE_HINT：存活但看不见的Fragment生命周期也能走到onResume()。 已废弃！
 * BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT：存活但看不见的Fragment生命周期最多走到onStart()。
 *
 */
class VpFragmentAdapter(var data: List<Fragment>, fm: FragmentManager,
                        behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT) :
        FragmentPagerAdapter(fm, behavior) {


    override fun getCount(): Int {
        return if (CollectionUtils.isEmpty(data)) 0 else data.size
    }

    override fun getItem(position: Int): Fragment {
        return data[position]
    }

//    override fun getItemId(position: Int): Long {
//        return data[position].hashCode().toLong()
//    }

}