package com.zkp.breath.kotlin

import android.util.Log

/**
 * Created b Zwp on 2019/7/19.
 *
 * 在main方法中和android环境中执行的结果好像是不一样的.有时间做下测试
 */

fun xxx() {

    var z: Int = 128        // 调试后z对应java的int类型
    var a: Int? = z         // a对应java的Integer类型
    var b: Int? = a         // 把a的内存地址赋值给b引用
    println("是否相等2:${a === b}")
    // false;identity equality for arguments of types Int and Int? can be unstable beacause of implicit boxing
    // 简单说就是拆箱和封箱是没办法进行内存比较的
    println("是否相等2:${z === b}")

    var q: Int? = 128
    var w: Int? = 128
    var e: Int? = 128
    println("是否相等3:${w === e}")


    var i: Int? = 128
    var o: Int? = i
    var p: Int? = i
    println("是否相等4:${o === p}")
}


fun main() {
    val t = -125
    val t1: Int? = t
    val t2: Int? = t
    println("是否相等1" +(t1 === t2))


    var z: Int = 128
    var a: Int? = z
    var b: Int? = z
    println("是否相等2" +(a === b))

}