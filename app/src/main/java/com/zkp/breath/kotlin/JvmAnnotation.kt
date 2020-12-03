package com.zkp.breath.kotlin


/**
 * JvmOverloads注解：必须存在一个默认值的参数，生成多个重载函数。
 * 没有默认值的为一个函数，然后没有默认值逐渐搭配有默认值的为一个函数。如下：
 *
 * void f(String s)
 * void f(String s, boolean b)
 * void f(String s, boolean b, int i)
 */
@JvmOverloads
fun jvmOverloads(s: String, b: Boolean, i: Int = 1) {

}