package com.zkp.breath.kotlin

import android.util.Log

/**
 * Created b Zwp on 2019/7/19.
 */

fun xxx() {

    var z: Int = 128
    var a: Int? = z
    var b: Int? = a
    Log.i("是否相等2", ": "+(a === b))


    var q: Int? = 128
    var w: Int? = 128
    var e: Int? = 128
    Log.i("是否相等3", ": "+(w === e))


    var i: Int? = 128
    var o: Int? = i
    var p: Int? = i
    Log.i("是否相等4", ": "+(o === p))
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