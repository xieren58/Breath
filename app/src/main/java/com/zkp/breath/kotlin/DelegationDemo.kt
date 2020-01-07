package com.zkp.breath.kotlin

interface S {
    fun getTask()
}

class MI(name: String) : S {
    override fun getTask() {
        println("子类重写")
    }
}

class Proxy(one: S) : S by one

fun main(args: Array<String>) {
    val one = MI("小明")
    Proxy(one).getTask()
}