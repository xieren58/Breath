package com.zkp.breath.kotlin

/**
 * 本地函数（嵌套函数）：不想将一段逻辑作为单独的函数对外暴露（有点鸡肋的作用）
 */

fun login(user: String, password: String, illegalStr: String) {
    fun validate(value: String, illegalStr: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(illegalStr)
        }
    }
    validate(user, illegalStr)
    validate(password, illegalStr)
}