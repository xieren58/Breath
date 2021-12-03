package com.zkp.breath.coroutines

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.internal.wait

/**
 *
 * https://juejin.cn/post/6844904163424337934
 * https://juejin.cn/post/6909276222895685640#heading-3
 * https://juejin.cn/post/6930881112415666184
 * https://juejin.cn/post/6901956626324914184#heading-3
 * https://juejin.cn/post/6988876911338323981
 *
 *
 * 协程异常：
 * 1. 异常是否会传递，和Job和SupervisorJob这两个有关
 * 2. 异常的处理，不同协程构建器处理不同，launch和async。
 *
 *  * 一般而言，协程发生异常，会做以下几件事：。/。(只适用于coroutineScope)
 * 1. 异常传播给它的父协程
 * 2. 父协程取消其它子协程
 * 3. 父协程取消自己
 * 4. 递归第1步
 *
 * Job和SupervisorJob ：
 * 0. 两者都是一个协程上下文
 * 1. 如果想让子协程的失败不会影响其他的子协程，那么在创建CoroutineScope的CoroutineContext的时候，可以选择SupervisorJob。其实就是不传播异常到父协程，但是需要对应
 *    的子协程处理异常， 可以提供异常处理器 CoroutineExceptionHandler。
 * 2. 如果使用的是CoroutineContext使用的是Job，异常也是会传播到父协程，那么即便你在对应的子协程使用CoroutineExceptionHandler也不能处理异常，这时就应该要在父协程进行处理。
 * 3. 无论你使用哪种类型的 Job，未捕获异常最终都会被抛出。如果不知道在那一层设置CoroutineExceptionHandler，那么可以在感觉会有问题的协程的协程体中进行try-catch。
 * 4. 警告：SupervisorJob 仅在属于下面两种作用域时才起作用：使用 supervisorScope 或者 CoroutineScope(SupervisorJob()) 创建的作用域。
 *
 *
 * 协程的异常处理
 * 0.只能捕获对应协程内未捕获的异常
 * 1.功能相当于Thread.setUncaughtExceptionHandler进行异常的全局自定义处理
 * 2.相当于ContinuationInterceptor拦截器，二者都是一个协程上下文，只是拦截输出的内容不一样。
 * 3.通过 async 启动的协程出现未捕获的异常时会忽略 CoroutineExceptionHandler，这与 launch 的设计思路是不同的。
 * 4.异常传播和协程作用域相关
 */

fun coroutineExceptionHandlerDemo() {
    lanchExceptionHandle()
    asyncExceptionHandle()
}

fun asyncExceptionHandle() {

    /**
     * 当async在根协程CoroutineScope实例使用时，异常不会被自动抛出，而是直到你调用 .await() 时才抛出。
     */
    fun asyncRootAnyJob() {
        // 这里的CoroutineScope的CoroutineContext无论是Job还是SupervisorJob
        val scope = CoroutineScope(SupervisorJob())
        scope.async {
            Log.i("asyncAnyJobRoot", "测试是否被取消111")
            throw  NullPointerException()
        }

        // 这个的sync也是在根协程CoroutineScope实例使用，所以异常不会被自动抛出
//        val scope = CoroutineScope(Job())
//        scope.launch {
//            val scope1 = CoroutineScope(Job())
//            scope1.async {
//                throw  NullPointerException()
//            }
//        }
    }

    fun asyncRootAnyJobTry() {
        val scope = CoroutineScope(Job())
        val async = scope.async {
            Log.i("asyncRootAnyJobTry", "测试是否被取消111")
            throw  NullPointerException()
        }

        scope.launch {
            try {
                async.wait()
            } catch (e: Exception) {
                Log.i("asyncRootAnyJobTry", "try住了异常")
            }
        }
    }

    fun asyncJobTry() {
        val scope = CoroutineScope(Job())
        scope.launch {
            // CoroutineScope的CoroutineContext为Job，
            val deferred = async {
                Log.i("asyncJobTry", "测试是否被取消111")
                try {
                    throw  NullPointerException()
                } catch (e: Exception) {
                }
            }
        }
    }

    fun asyncJobHandler() {
        val scope = CoroutineScope(Job())
        scope.launch(exceptionHandler) {
            val deferred = async {
                Log.i("asyncJobHandler", "测试是否被取消111")
                throw  NullPointerException()
            }
        }
    }

//    asyncRootAnyJob()
//    asyncRootAnyJobTry()
//    asyncJobTry()
//    asyncJobHandler()

}

fun lanchExceptionHandle() {

    fun launchJobTry() {
        // 使用Job，异常会传递
        val scope = CoroutineScope(Job())
        scope.launch {  // 1
            launch {    // 2，1的子协程
                launch {    // 3，2的子协程
                    delay(1000L)    // 延迟，提高执行4的机会
                    Log.i("launchJobTry", "测试是否被取消111")
                    // 使用try捕获处理了异常，内部已经消化了这个异常，那么异常就不会抛出，不会抛出则不会发生传递。
                    try {
                        throw  NullPointerException()
                    } catch (e: Exception) {
                        Log.i("launchJobTry", "捕获到了异常")
                    }
                }
                launch {     // 4，2的子协程
                    delay(2000L)    // 延迟，提高执行3的机会
                    Log.i("launchJobTry", "测试是否被取消222")
                }
            }
        }
    }

    /**
     * 下面这个例子中，CoroutineExceptionHandler要在存在父子关系层级中最顶级那一层进行设置（1处），否则无效，异常最终还是会被抛出。
     * 如果重新创建一个CoroutineScope，然后在2处使用，这时候1和2不存在父子关系，那么CoroutineExceptionHandler放在2处即可。
     */
    fun launchJobHandler() {
        // 使用Job，异常会传递
        val scope = CoroutineScope(Job())
        scope.launch(exceptionHandler) {  // 1
            launch {    // 2，1的子协程
                launch {    // 3，2的子协程
                    delay(1000L)    // 延迟，提高执行4的机会
                    Log.i("launchJobHandler", "测试是否被取消111")
                    throw  NullPointerException()   // 没有及时处理，异常抛出
                }
                launch {     // 4，2的子协程
                    delay(2000L)    // 延迟，提高执行3的机会
                    Log.i("launchJobHandler", "测试是否被取消222")
                }
            }
        }
    }

    fun launchSuperJobHandlerFs() {
        // 需要注意的是2是1的子协程，但是2这里的的其实还是一个新的job
        val scope = CoroutineScope(SupervisorJob())
        scope.launch(exceptionHandler) { //1
            launch {//2
                delay(1000L)    // 延迟，提高执行4的机会
                Log.i("launchSuperJobHandlerFs", "测试是否被取消111")
                throw  NullPointerException()   // 没有及时处理，异常抛出
            }
            launch {
                delay(2000L)    // 延迟，提高执行3的机会
                Log.i("launchSuperJobHandlerFs", "测试是否被取消222")
            }
        }
    }

    fun launchSuperJobHandlerBr() {
        // 2和1是兄弟关系，因为2用的也是SupervisorJob的CoroutineScope。（SupervisorJob，异常不会传递，需要子协程自己设置exceptionHandler）
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {//1
            scope.launch(exceptionHandler) {//2
                delay(1000L)    // 延迟，提高执行4的机会
                Log.i("launchSuperJobHandlerBr", "测试是否被取消111")
                throw  NullPointerException()   // 没有及时处理，异常抛出
            }
            scope.launch {
                delay(2000L)    // 延迟，提高执行3的机会
                Log.i("launchSuperJobHandlerBr", "测试是否被取消222")
            }
        }
    }

    fun launchSuperScope1() {
        val scope = CoroutineScope(Job())
        scope.launch {//1
            supervisorScope {
                scope.launch(exceptionHandler) {//2，和1是兄弟协程，虽然在supervisorScope里面，但是这里但CoroutineScope还是Job
                    delay(1000L)
                    Log.i("launchSuperScope", "测试是否被取消111")
                    throw  NullPointerException()
                }
                scope.launch {//3，和1是兄弟协程，虽然在supervisorScope里面，但是这里但CoroutineScope还是Job
                    delay(2000L)
                    Log.i("launchSuperScope", "测试是否被取消222")
                }
            }
        }
    }

    fun launchSuperScope2() {
        val scope = CoroutineScope(Job())
        scope.launch {
            supervisorScope {
                launch(exceptionHandler) {
                    delay(1000L)
                    Log.i("launchSuperScope2", "测试是否被取消111")
                    throw  NullPointerException()
                }
                launch {
                    delay(2000L)
                    Log.i("launchSuperScope2", "测试是否被取消222")
                }
            }
            delay(4000L)
            Log.i("launchSuperScope2", "测试是否被取消333")
        }
    }

    fun launchSuperScope3() {
        val scope = CoroutineScope(Job())
        scope.launch {
            supervisorScope {
                launch(exceptionHandler) {
                    launch {
                        delay(3000L)
                        Log.i("launchSuperScope3", "测试是否被取消333")
                    }
                    delay(1000L)
                    Log.i("launchSuperScope3", "测试是否被取消111")
                    throw  NullPointerException()
                }
                launch {//3，和1是兄弟协程
                    delay(2000L)
                    Log.i("launchSuperScope3", "测试是否被取消222")
                }
                delay(4000L)
                Log.i("launchSuperScope3", "测试是否被取消4444")
            }
            delay(5000L)
            Log.i("launchSuperScope3", "测试是否被取消5555")
        }
    }

//    launchJobTry()
//    launchJobHandler()
//    launchSuperJobHandlerFs()
//    launchSuperJobHandlerBr()
//    launchSuperScope1()
//    launchSuperScope2()
//    launchSuperScope3()
}

/**
 * 协程的异常捕获器
 *
 * 1. 是一种不得已的全局捕获机制。在CoroutineExceptionHandler中，无法从异常中恢复。当调用CoroutineExceptionHandler时，协程也伴随相应的异常结束。
 *    通常，CoroutineExceptionHandler用于输出异常日志，显示某错误消息，终止或重新启动应用程序。
 *
 * 2. 如果您需要在代码的特定部分处理异常，建议在协程内部使用try/catch包裹相应代码。这样，您可以避免协程的异常终止，重试操作或采取其他措施”
 *
 * 3. 通过直接在协程中处理异常，try-catch我们没有利用结构化并发中取消的相关特性。例如，让我们假设我们并行启动两个协程。他们俩都相互依赖，如果一个失败了，
 *    那么另一个的完成也就没有意义。如果我们现在在每个协程中使用try-catch来处理异常，则异常不会传播到父级，因此其他协程也不会被取消。而这就造成了资源的浪费。
 *    在这种情况下，我们应该使用CoroutineExceptionHandler。
 */
val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.i("异常捕获器", "coroutineExceptionHandler: ${throwable.message}")
}
