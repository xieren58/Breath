package com.zkp.breath.coroutines

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.lang.NullPointerException

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

//    launchMistake()
//    asyncMistake()
    coroutineScopeMistake()
}

fun coroutineScopeMistake() {
    val topLevelScope = CoroutineScope(Job())
    topLevelScope.launch {
        try {
            coroutineScope {
                launch {
                    suspendCoroutineExceptionTask()
                }
            }
        } catch (exception: Exception) {
            println("Handle $exception in try/catch")
        }
    }
}

/**
 * launch和async协程中未捕获的异常会立即在作业层次结构中传播。但是，如果顶层Coroutine是从launch启动的，则异常将由CoroutineExceptionHandler
 * 处理或传递给线程的未捕获异常处理程序。如果顶级协程以async方式启动，则异常封装在Deferred返回类型中，并在调用.await（）时重新抛出。
 */
private fun asyncMistake() {


    fun topCoroutine() {
        val coroutineScope = CoroutineScope(Job())
        val async = coroutineScope.async {
            Log.i("async异常捕获", "asyncMistake: ")
            suspendCoroutineExceptionTask()
        }

        /**
         * launch没有返回值，async可以获取携程的结果，如果异步协程失败，则将该异常封装在Deferred返回类型中，
         * 并在我们调用suspend函数.await（）来检索其结果值时将其重新抛出。如果没有调用await，那么上面执行后程序是不会崩溃的。
         */
        GlobalScope.launch {
            try {
                async.await()
            } catch (exception: Exception) {
                Log.i("async异常捕获", "asyncMistake: ")
            }
        }

    }

    /**
     * 如果async协程是顶级协程，则会将异常封装在Deferred中,等待调用await才会抛出异常。否则，该异常将立即传播到Job层次结构中，
     * 并由CoroutineExceptionHandler处理，甚至传递给线程的未捕获异常处理程序，即使不对其调用.await（），如以下示例所示：
     */
    fun noTopCoroutine() {
        val coroutineScope = CoroutineScope(Job())
        coroutineScope.launch(exceptionHandler) {
            async {
                Log.i("async异常捕获", "asyncMistake: ")
                suspendCoroutineExceptionTask()
            }
        }
    }

//    topCoroutine()
    noTopCoroutine()
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

    // 错误的try-catch，需要在对应协程内处理，和作用域无关
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
    fun differentScopeCorrect() {
        GlobalScope.launch {
            GlobalScope.launch(exceptionHandler) {
                suspendCoroutineExceptionTask()
            }
        }
    }

    // GlobalScope，同一个作用域，正确的CoroutineExceptionHandler设置
    fun sameScopeCorrect() {
        GlobalScope.launch(exceptionHandler) {
            launch {
                suspendCoroutineExceptionTask()
            }
        }
    }

    // 自定义CoroutineScope，同一个作用域，正确的CoroutineExceptionHandler设置
    fun sameScopeCorrect1() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch(exceptionHandler) {
            launch {
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }
    }

//    tryCatchCorrect()
//    tryCatchMistake()
//    differentScopeCorrect()
//    sameScopeCorrect()
//    sameScopeCorrect1()
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
