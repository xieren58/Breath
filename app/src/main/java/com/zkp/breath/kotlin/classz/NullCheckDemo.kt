package com.zkp.breath.kotlin.classz

fun main() {
}

private class NullCheckClass {

    fun f1(x: String?) {
        // 为空则抛出空指针异常
        println(x!!.toInt())
    }

    fun f2(x: String?) {
        // 为空则不做处理返回null
        println(x?.toInt())
    }

    fun f3(x: String?) {
        // 为空可返回指定的值
        println(x?.toInt() ?: -1)
    }

}

