package com.zkp.breath.kotlin

fun main() {
}

private class NullCheckClass {

    fun f1(x: String?) {
        // 为空则抛出空指针异常
        println(x!!.toInt())
    }

    /**
     * ？.是一种线程安全的写法
     */
    fun f2(x: String?) {
        // 为空则不做处理返回null
        println(x?.toInt())
    }

    fun f3(x: String?) {
        // 为空可返回指定的值
        println(x?.toInt() ?: -1)
    }

}

