package com.zkp.breath.arouter.interfaces

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.zkp.breath.arouter.IProviderServicePath

@Route(path = IProviderServicePath.HELLO_PROVIDER_SERVICE_PATH)
class HelloServiceImpl : HelloService {

    private var mContext: Context? = null

    override fun sayHello(name: String?): String? {
        return "hello, $name"
    }

    override fun init(context: Context) {
        mContext = context
    }
}