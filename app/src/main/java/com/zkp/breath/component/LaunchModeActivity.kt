package com.zkp.breath.component

import com.didichuxing.doraemonkit.kit.core.BaseActivity

/**
 * https://www.bilibili.com/video/BV1CA41177Se
 *
 *
 * taskAffinity：一个App可以存在多个Task，但在Android里一个App默认最多只有一个Task可以显示在最近任务列表，而用来
 *              甄别这个唯一性就是taskAffinity。清单文件中Activity都可以设置taskAffinity，相当于分组，默认取值
 *              所在Application的taskAffinity，而Application的taskAffinity默认取自app的包名。另外每个Task
 *              也有它的taskAffinity，它的值取自栈底的 Activity的 taskAffinity，我们可以通过Android Manifest.xml
 *              来定制 taskAffinity，但是默认情况下一个App所有的Task的taskAffinity都是一样的（包名）。
 *
 * standard：在不同Task中打开同一个 Activity，Activity会被创建多个实例，分别放进每一个Task，互不干扰。
 * singleTask：1.被别的App启动的时候，不会进入启动它的Task里，而是会在属于它自己的Task里创建，然后把整个Task拿过来
 *             压在启动它的Task的上面，这种方式打开的Activity的入场动画是应用间切换的动画。如果这时候点击返回键，
 *             会等上面的Task消失（即App所有Activity全部关闭）下面的Task才会显示（会有一个应用间切换的动画）。
 *
 *             2.即保证只有一个Task里有这个 Activity，又保证这个Task里最多只有一个这个 Activity。如果Task已经存在
 *             这个Activity，那么不再创建新的对象，而是复用这个已有的对象同时因为Activity没有被重建，系统就不会调用
 *             它的onCreate()方法而是调用OnNewIntent()方法从Intent里解析数据刷新界面；如果调用OnNewIntent()
 *             之前这个Activity上面压着其他Activity，系统也会把这些Activity全部清掉来确保目标Activity出现在占栈顶。
 *
 *             3.不止应用内部可以叠成栈，Task之间也可以，但Task之间的叠加只适用于前台Task，在进入后台的第一时间
 *             会被拆开。
 *                >Task由前台进入后台：
 *                    1.Home键返回桌面。
 *                    2.最近任务键（方块键）查看最近任务。
 *
 *
 *
 *
 *
 *
 * 最近任务列表：可以看到不同app，其实准确来说是不同的Task，这些Task有可能已经销毁只是一个残影（方便用户重新打开，
 *              其实就是重新启动app）
 *
 * Task：任务栈，回退栈。
 *
 * standard：在不同Task中打开同一个 Activity，Activity会被创建多个实例，分别放进每一个Task，互不干扰。
 *
 *          例子：如果A_Task多次打开A_Activity，那么A_Activity会被创建多次，然后压入A_Task中。
 *                如果A_Task打开了B_Task的B_Activity，那么会创建一个B_Activity实例，然后压入A_Task中，如果
 *                B_Task不存在也不会创建B_Task。
 *
 * singleTask：即保证只有一个Task里有这个 Activity，又保证这个Task里最多只有一个这个 Activity。如果Task已经存在
 *             这个Activity，那么不再创建新的对象，而是复用这个已有的对象同时因为Activity没有被重建，系统就不会调用
 *             它的onCreate()方法而是调用OnNewIntent()方法从Intent里解析数据刷新界面；如果调用OnNewIntent()
 *             之前这个Activity上面压着其他Activity，系统也会把这些Activity全部清掉来确保目标Activity出现在占栈顶。
 *
 *            例子：如果A_Task多次打开A_Activity，那么A_Task只会存在一个A_Activity。
 *                 如果A_Task打开B_Task的B_Activity，如果B_Task不存在则先创建且B_Task只会存在一个B_Activity。
 *
 * singleInstance：singleTask严格版，新建一个Task用于存在Activity，即保证只有一个Task里有这个 Activity，
 *              又保证这个Task里最多只有一个这个Activity并且这个Task不允许存在其他Activity，目标Activity
 *              独占了Task，而后续新加入的栈会添加到原来的Task中。
 *
 */
class LaunchModeActivity : BaseActivity() {
    //
}