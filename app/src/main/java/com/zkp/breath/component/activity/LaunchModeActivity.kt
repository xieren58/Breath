package com.zkp.breath.component.activity

import com.zkp.breath.component.activity.base.BaseActivity


/**
 * https://www.bilibili.com/video/BV1CA41177Se
 *
 * Task：每一个Task都有一个回退栈，纪录Activity压入顺序。
 *
 * 最近任务列表：可以看到不同app，其实准确来说是不同的Task，这些Task有可能已经销毁只是一个残影（方便用户重新打开，
 *              其实就是重新启动app）；有些Task虽然看不见，但其实是存活的，只是因为taskAffinity冲突（
 *              launchMode为singleInstance情况下）。
 *
 * taskAffinity：一个App可以存在多个Task，但在Android里一个App默认最多只有一个Task可以显示在最近任务列表，而用来
 *              甄别这个唯一性就是taskAffinity。清单文件中Activity都可以设置taskAffinity，相当于分组，默认取值
 *              所在Application的taskAffinity，而Application的taskAffinity默认取自app的包名。另外每个Task
 *              也有它的taskAffinity，它的值取自栈底的 Activity的 taskAffinity，我们可以通过Android Manifest.xml
 *              来定制 taskAffinity，但是默认情况下一个App所有的Task的taskAffinity都是一样的（包名）。
 *
 * standard：在不同Task中打开同一个 Activity，Activity会被创建多个实例，分别放进每一个Task，互不干扰。
 * singleTop：standard升级版，只是当Task栈顶存在目标Activity的话就复用（通过OnNewIntent()）。
 *
 * singleTask(唯一性)：1.被别的App启动的时候，不会进入启动它的Task里，而是会在属于它自己的Task里创建，然后把整个Task拿过来
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
 *             4.设置launchMode = "singleTask"的Activity，系统会先对比Activity和当前Task的TaskAffinity是否
 *              相同。如果相同就正常入栈；如果不同Activity会去寻找和它相同TaskAffinity的Task后入栈，如果找不到系统
 *              会为它创建一个新的Task。
 *
 * singleInstance（唯一性和独占性）：singleTask严格版，创建一个新的Task单独存放，不允许存在其他的Activity。
 *
 *
 *
 * 实战的通常选择：
 *  1. standard，singleTop，singleTask 多用于App的内部。
 *  2. singleInstance 多用于开放给外部app来共享使用。
 *
 */
class LaunchModeActivity : BaseActivity() {
    //
}