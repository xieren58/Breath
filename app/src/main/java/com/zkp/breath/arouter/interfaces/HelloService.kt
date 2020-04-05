package com.zkp.breath.arouter.interfaces

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 这里的service是一种面向接口开发的概念，该接口必须继承IProvider。
 * 其实就是一个普通类，通过实现注解的方式实例化
 */
interface HelloService : IProvider {
    fun sayHello(name: String?): String?
}