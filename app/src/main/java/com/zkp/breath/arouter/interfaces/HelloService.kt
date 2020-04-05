package com.zkp.breath.arouter.interfaces

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 这里的service是一种面向接口开发的概念，该接口必须继承IProvider。
 * 其实就是一个普通类，在不同模块且无依赖的情况下通过这种形式能被调用，其实就和跳转activity一样。
 */
interface HelloService : IProvider {
    fun sayHello(name: String?): String?
}