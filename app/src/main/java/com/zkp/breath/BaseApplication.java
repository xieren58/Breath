package com.zkp.breath;

import androidx.multidex.MultiDexApplication;

import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

/**
 * Created b Zwp on 2019/7/25.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 常用工具库初始化
        com.blankj.utilcode.util.Utils.init(this);
        TUIKitInit();
    }

    /**
     * 腾讯云IM初始化（tuikit版本，包含UI）
     */
    private void TUIKitInit() {
        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        int sdkAppID = 1400266626;
        configs.setSdkConfig(new TIMSdkConfig(sdkAppID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());

        TUIKit.init(this, 1400266626, configs);
    }
}
