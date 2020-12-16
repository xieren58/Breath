package com.zkp.breath.component.fragment.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater

/**
 * Fragment支持构造函数传入布局，在onCreateView（）方法会判断布局id是否有效，有效则使用该布局。
 *
 * Fragment：依赖于Activity（也可以依赖于Fragment，但是最顶的父Fragment必须依赖Activity），不能独立存在。（其实
 *          可以看成是View，只是拥有更加丰富的生命周期）
 * FragmentManager：执行添加/移除/替换 fragment 并将这些操作加入到返回栈中的操作，这些操作被称为「事务」。
 * FragmentManagerImpl：FragmentManager的具体实现。
 * FragmentTransaction：事务，就是添加/移除/替换等操作，事务可以包含任意数量的操作。
 *
 * 返回栈：
 * 1. 调用addToBackStack()将事务存入返回栈，如果没添加该语句那么返回事件将传递给activity。
 * 2. 当调用 FragmentManager.popBackStack()或者用户点击返回键，会回退该事务（回退指的是如果事务是add(frag1)，
 *    那么回退操作就是remove(frag1)）。相当于事务的"逆操作"(可以看成Fragment主动拦截返回事件，然后进行逆向操作)
 *
 * popBackStack()的flag：
 * 0：执行盖在指定Fragment上所有Fragment存放在返回栈事务的逆操作。
 * 1(FragmentManager.POP_BACK_STACK_INCLUSIVE)：执行指定Fragment和盖在其上面的所有Fragment存放在返回栈事务的逆操作。
 *
 * 获取FragmentManager的方式：
 * 1. 每个 FragmentActivity 及其子类（如 AppCompatActivity）都可以通过getSupportFragmentManager() 来访问 FragmentManager。
 * 2. Fragment 也能管理一个或多个子 fragment（译者注：嵌套 fragment，即一个 fragment 的直接宿主可能是 activity
 *    或另一个 fragment）。在 fragment 中，您可以通过 getChildFragmentManager() 来获取管理子 fragment 的
 *    FragmentManager 实例。如果需要访问该 fragment 宿主的 FragmentManager，可以使用  getParentFragmentManager()。
 *
 * 提交方式：
 * 1. commit()不会立即执行事务，该事务会被安排为能够在主线程上尽快运行。可以通过调用 executePendingTransactions()
 *    执行已经调用 commit() 方法但没运行的事务，该方法与addToBackStack 兼容。对于绝大多数场景，使用 commit() 即可。
 * 2. commitNow() 立即在主线程上运行 fragment 事务，但是不能执行addToBackStack，否则会抛出异。
 *
 * show()和hide()：
 * show() 和 hide() 方法来显示和隐藏已添加到容器的 fragment 的 view，这些方法设置 fragment view 的可见性而
 * 不影响 fragment 的生命周期。
 *
 * detach()和attach()：
 * 1. detach将fragment和onCreateView()创建的UI分离。 (onDestroyView())
 * 2. attach将重新连接UI并显示。（onCreateView() -> onResume()）
 * 3. 如果在同一事务中进行detach和attach操作，那么彼此抵消，从而避免了 fragment UI 的销毁和立即重建。
 * 4. attach() 和 detach() 方法与 Fragment 的 onAttach() 和 onDetach() 方法无关。
 *
 * setCustomAnimations():
 * 参数1：add()或者attach()操作会触发
 * 参数2：remove()和detach()操作会触发
 * 参数3：addToBackStack()前提下执行remove()，点击返回键触发
 * 参数4：addToBackStack()前提下执行add()，点击返回键触发
 *
 * setExitTransition()和setEnterTransition()：
 * 在Fragment的onCreate()调用setExitTransition()或setEnterTransition()
 *
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    val TAG: String? by lazy {
        this::class.simpleName
    }

    /**
     * 该上下文是Fragment的宿主Activity，需要使用到Activity的时候自己强转为Activity即可
     */
    lateinit var mContext: Context


    /**
     * onAttach：onAttach()在fragment与Activity关联之后调用。需要注意的是，初始化fragment参数可以从
     * getArguments()获得，但是，当Fragment附加到Activity之后，就无法再调用setArguments()。
     * 所以除了在最开始时，其它时间都无法向初始化参数添加内容。
     *
     * 解决getActivity()可能返回null，requireActivity()内部也是调用getActivity()只是会对结果进行判断，
     * 判断为null会抛出异常，所以这个方法调用的时候需要try-catch。
     *
     * 如果在创建Fragment时要传入参数，必须要通过setArguments(Bundle bundle)方式添加，而不建议通过为Fragment
     * 添加带参数的构造函数，因为通过setArguments()方式添加，在由于内存紧张导致Fragment被系统杀掉并恢复
     * （re-instantiate）时能保留这些数据，这是官方建议的做法。
     *
     * 如果要获取setArguments(Bundle bundle)设置的参数，我们可以在下面的onAttach()中通过getArguments()
     * 获取。
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
        val arguments = arguments
        Log.i(tag, "onAttach()")
    }


    /**
     * onCreate：fragment初次创建时调用。尽管它看起来像是Activity的OnCreate()函数，但这个只是用来创建Fragment的。
     * 此时的Activity还没有创建完成，因为我们的Fragment也是Activity创建的一部分。所以如果你想在这里使用Activity
     * 中的一些资源，将会获取不到，如果想要获得Activity相关联的资源，必须在onActivityCreated中获取。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag, "onCreate()")

        val inflater = TransitionInflater.from(requireContext())
        if (enterTransition() > 0) {
            enterTransition = inflater.inflateTransition(enterTransition())
        }
        if (exitTransition() > 0) {
            exitTransition = inflater.inflateTransition(exitTransition())
        }
    }

    /**
     * R.transition.fragment_enter_transition
     * R.transition.fragment_exit_transition
     */
    open fun enterTransition() = 0
    open fun exitTransition() = 0

    /**
     * onCreateView：在这个fragment构造它的用户接口视图(即布局)时调用。
     *
     * 该方法返回Fragment的UI布局，需要注意的是inflate()的第三个参数是false，因为在Fragment内部实现中，会把该
     * 布局添加到container中，如果设为true，那么就会重复做两次添加，则会抛如下异常：
     * Caused by: java.lang.IllegalStateException: The specified child already has a parent.
     * You must call removeView() on the child's parent first.
     *
     * 使用getView()可以获取布局View。
     *
     * 1.重写后使用ViewBinding的方式传入布局。
     * 2.如果使用主构造函数传入布局则不需要重写该方法。
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(tag, "onCreateView()")
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        val viewBinding = viewBinding(inflater, container)
        if (onCreateView == null && viewBinding == null) {
            throw IllegalStateException("请调用构造函数传入LayoutRes或者有效调用viewBinding()返回所属Fragment的布局")
        }
        if (onCreateView != null && viewBinding != null) {
            // viewbinding创建方式优先
            throw IllegalStateException("同时实现了viewBinding和构造方法传入布局，推荐使用构造方法传入")
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
    open fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean = false): View? {
        return null
    }

    /**
     * onViewCreated：fragment的视图构建完成。
     * 在这个方法可以对view进行一些初始化操作。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tag, "onViewCreated()")
    }

    /**
     * 使用前需要使用下面的方法打开拦截容器Activity的回退方法，然后子Fg重写handleActivityOnBackPressed()
     * 进行自己的拦截操作。
     *
     * @see OnBackPressedCallback.setEnabled(boolean)  true拦截，false不拦截
     * @see onActivityCreated 推荐在此方法进行调用
     */
    protected fun handleActivityOnBackPressed(callback: OnBackPressedCallback) {
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), callback)
    }

    /**
     * onActivityCreated：在Activity的OnCreate()结束后，会调用此方法。所以到这里的时候Activity已经创建完成，
     * 在这个函数中才可以使用Activity的所有资源。
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(tag, "onActivityCreated()")
    }

    /**
     * onStart：当到OnStart()时，Fragment对用户就是可见的了，但用户还未开始与Fragment交互。在生命周期中也可以
     * 看到Fragment的OnStart()过程与Activity的OnStart()过程是绑定的，意义即是一样的，以前你写在Activity的
     * OnStart()中来处理的代码，用Fragment来实现时，依然可以放在OnStart()中来处理。
     */
    override fun onStart() {
        super.onStart()
        Log.i(tag, "onStart()")
    }

    /**
     * onResume：当这个fragment对用户可见并且正在运行时调用。从生命周期对比中可以看到Fragment的OnResume与
     * Activity的OnResume是相互绑定的，意义是一样的。当OnResume()结束后，就可以正式与用户交互了。
     */
    override fun onResume() {
        super.onResume()
        Log.i(tag, "onResume()")
    }

    /**
     * onPause：此回调与Activity的OnPause()相绑定，与Activity的OnPause()意义一样。
     */
    override fun onPause() {
        super.onPause()
        Log.i(tag, "onPause()")
    }

    /**
     * onStop：这个回调与Activity的OnStop()相绑定，意义一样。
     * 一般切到桌面会走onPause和onStop，切回则会重走onStart和onResume。
     */
    override fun onStop() {
        super.onStop()
        Log.i(tag, "onStop()")
    }

    /**
     * 如果Fragment即将被结束或保存，会将在onCreateView创建的视图与这个fragment分离。
     */
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(tag, "onDestroyView()")
    }

    /**
     * onDestroy：当这个fragment不再使用时调用。需要注意的是，它即使经过了onDestroy()阶段，
     * 但仍然能从Activity中找到，因为它还没有Detach。
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "onDestroy()")
    }

    /**
     * onDetach：Fragment与Activity解除绑定，它的所有资源都将被释放。
     */
    override fun onDetach() {
        super.onDetach()
        Log.i(tag, "onDetach()")
    }

    /**
     * FragmentTransaction的commit()操作是异步的，内部通过mManager.enqueueAction()加入处理队列。
     * 对应的同步方法为commitNow()，commit()内部会有checkStateLoss()操作，如果开发人员使用不当（比如commit()操
     * 作在onSaveInstanceState()之后），可能会抛出异常，而commitAllowingStateLoss()方法则是不会抛出异常版本
     * 的commit()方法，但是尽量使用commit()，而不要使用commitAllowingStateLoss()。
     *
     * addToBackStack("fname")是可选的。FragmentManager拥有回退栈（BackStack），类似于Activity的任务栈，
     * 如果添加了该语句，就把该事务加入回退栈（压栈），当用户点击返回按钮，会回退该事务（回退指的是如果事务是add(frag1)，
     * 那么回退操作就是remove(frag1)，即出栈）；如果没添加该语句，用户点击返回按钮会直接销毁Activity。
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}