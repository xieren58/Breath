package com.zkp.breath.arouter


/**
 * 所有Activity的path都放在此文件统一管理。
 * 命名规范："/模块名/类名"，注意路径至少两级
 */
object ActivityRouterPath {
    const val TEST1_AROUTER_ACTIVITY_PATH = "/app/test1"
    const val TEST2_AROUTER_ACTIVITY_PATH = "/app/test2"
    const val TEST3_AROUTER_ACTIVITY_PATH = "/app/test3"
    const val TEST4_AROUTER_ACTIVITY_PATH = "/app/test4"
    const val TEST5_AROUTER_ACTIVITY_PATH = "/app/test5"
}

/**
 * 所有Fragment的path都放在此文件统一管理。
 * 命名规范："/模块名/类名"，注意路径至少两级
 */
object FragmentRouterPath {

}

/**
 * 所有暴露接口的path都放在此文件统一管理。
 * 命名规范："/模块名/类名"，注意路径至少两级
 */
object IProviderServicePath {
    const val HELLO_PROVIDER_SERVICE_PATH = "/providerService/hello"
}

/**
 * 所有router的拦截器priority
 * 16进制是防止有特殊拦截顺序的情况，可以有空余空间插入
 */
object InterceptorPriority {
    /**
     * simple类渎独自占据01系列
     */
    const val SIMPLE_INTERCEPTOR_PRIORITY = 0x01000001

    /**
     * 一般的拦截器从02系类开始
     */
    const val A_INTERCEPTOR_PRIORITY = 0x02000001
    const val B_INTERCEPTOR_PRIORITY = 0x02000002
}
