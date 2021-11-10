package com.zkp.breath.coroutines

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.internal.wait

/**
 * 协程的异常处理
 *
 * 0.只能捕获对应协程内未捕获的异常
 * 1.功能相当于Thread.setUncaughtExceptionHandler进行异常的全局自定义处理
 * 2.相当于ContinuationInterceptor拦截器，二者都是一个协程上下文，只是拦截输出的内容不一样。
 * 3.通过 async 启动的协程出现未捕获的异常时会忽略 CoroutineExceptionHandler，这与 launch 的设计思路是不同的。
 * 4.异常传播和协程作用域相关
 */
fun coroutineExceptionHandlerDemo() {

    launchMistake()
//    asyncMistake()
}


private fun asyncMistake() {


    val async = GlobalScope.async {
        suspendCoroutineExceptionTask()
    }


}

/**
 * 异常处理的错误例子
 *
 * 如果协程内部不使用try-catch语句处理异常，则该异常也不会re-thrown，因此不能由外部try-catch语句处理。相反，该异常是“在Job层次结构中传播”，可以由已配置的
 * CoroutineExceptionHandler处理。如果未配置CoroutineExceptionHandler，则异常会到达UncaughtExceptionHandler。
 */
private fun launchMistake() {

    // 正确的try-catch
    fun tryCatchCorrect() {
        GlobalScope.launch {
            GlobalScope.launch {
                try {
                    suspendCoroutineExceptionTask()
                } catch (e: Exception) {
                    Log.i("捕获异常的错误例子", "mistake: ")
                }
            }
        }
    }

    // 错误的try-catch，需要在对应协程内处理。
    fun tryCatchMistake() {
        GlobalScope.launch {
            try {
                GlobalScope.launch {
                    suspendCoroutineExceptionTask()
                }
            } catch (e: Exception) {
                Log.i("捕获异常的错误例子", "mistake: ")
            }
        }
    }

    // 不同作用域，正确的CoroutineExceptionHandler设置
    fun exceptionHandlerCorrect() {
        GlobalScope.launch {
            GlobalScope.launch(exceptionHandler) {
                suspendCoroutineExceptionTask()
            }
        }
    }

    // 不同作用域，错误的CoroutineExceptionHandler设置
    fun exceptionHandlerMistake() {
        GlobalScope.launch(exceptionHandler) {
            GlobalScope.launch {
                suspendCoroutineExceptionTask()
            }
        }
    }

    // 同一个作用域，正确的CoroutineExceptionHandler设置
    fun coroutineScopeCorrect() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch(exceptionHandler) {
            launch {
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }
    }

    // 同一个作用域，错误的CoroutineExceptionHandler设置
    fun coroutineScopeMistake() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            launch(exceptionHandler) {
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }
    }

    fun coroutineScopeCorrect22() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch(exceptionHandler) {
            topLevelScope.launch {
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }
    }

    // GlobalScope的例子
//    tryCatchCorrect()
//    tryCatchMistake()
//    exceptionHandlerCorrect()
//    exceptionHandlerMistake()


    // CoroutineScope的例子
//    coroutineScopeCorrect()
//    coroutineScopeMistake()
    coroutineScopeCorrect22()
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
