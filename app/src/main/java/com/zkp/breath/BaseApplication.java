package com.zkp.breath;


import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.ProcessUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;


/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMainProcess()) {
            // 常用工具库初始化
            com.blankj.utilcode.util.Utils.init(this);
            // 初始化界面卡顿检查工具
            BlockCanary.install(this, new BlockCanaryContext()).start();
        }
    }

//    private void initTinker() {
//        // 我们可以从这里获得Tinker加载过程的信息
//        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//
//        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
//        TinkerPatch.init(tinkerApplicationLike)
//                .reflectPatchLibrary()
//                .setPatchRollbackOnScreenOff(true)
//                .setPatchRestartOnSrceenOff(true)
//                .setFetchPatchIntervalByHours(3);
//
//        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
//        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
//    }

}
