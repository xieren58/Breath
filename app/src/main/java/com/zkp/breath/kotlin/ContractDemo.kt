package com.zkp.breath.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * https://juejin.im/post/5c0150f8e51d4540611e15e0#heading-3
 *
 * Contract契约（目前还处于实验阶段）：Kotlin中的Contract契约是一种向编译器通知函数行为的方法。（重点是ContractBuilder.kt和Effect.kt两个文件）
 * 1、我们只能在顶层函数体内使用Contract契约，即我们不能在成员和类函数上使用它们。
 * 2、Contract调用声明必须是函数体内第一条语句
 */

fun isValid(token: String?): Boolean {
    return token != null && token.isNotBlank()
}

// 由于Contract契约API还是Experimental（可以查看contract函数的定义就有Experimental注解），所以需要使用ExperimentalContracts注解声明。
@ExperimentalContracts
fun isValidContract(token: String?): Boolean {
    /**
     * 这里契约的意思是: 调用isValidContract函数，会产生这样的效果: 如果返回值是true, 那就意味着token != null.
     * 把这个契约行为告知到给编译器，编译器就知道了下次碰到这种情形，你的token就是非空的，自然就smart cast了。
     */
    contract {
        returns(true) implies (token != null)
    }
    return token != null && token.isNotBlank()
}


@ExperimentalContracts
fun main() {
    val s: String? = ""

    // 智能推导
    if (s != null) {
        println("smart cast： ${s.length}")
    }

    // 使用自己的判断函数，还是需要空安全的调用方式
    if (isValid(s)) {
        println("无契约自定义函数： ${s?.length}")
    }

    // 契约自定义函数，智能推导
    if (isValidContract(s)) {
        println("契约自定义函数： ${s.length}")
    }

    /**
     * isNullOrBlank()中的告诉编译器:调用isNullOrBlank()扩展函数产生效果是如果该函数的返回值是false，那么就意
     * 味着当前CharSequence实例不为空。所以我们可以发现一个细节当你调用isNullOrBlank()只有在取反的时候，
     * smart cast才会生效，不信可以自己试试。
     */
    if (!s.isNullOrBlank()) {
        println("isNullOrBlank： ${s.length}")
    }

    /**
     * requireNotNull()中的契约诉编译器:调用requireNotNull函数后产生效果是如果该函数正常返回，没有异常抛出，
     * 那么就意味着value不为空。
     */
    requireNotNull(s) {
        println("requireNotNull为null的时候执行")
    }

    // apply函数中的契约表示告诉编译器:调用apply函数后产生效果是指定block lamba表达式参数在适当的位置被调用。
    // 适当位置就是block lambda表达式只能在自己函数(这里就是指外层apply函数)被调用期间被调用，当apply函数被调用结束后，
    // block表达式不能被执行，并且指定了InvocationKind.EXACTLY_ONCE表示block lambda表达式只能被调用一次，
    // 此外这个外层函数还必须是个inline内联函数。
    s.apply {
        plus("apply函数")
    }
}