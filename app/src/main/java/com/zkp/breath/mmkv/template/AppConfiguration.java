package com.zkp.breath.mmkv.template;

import android.content.Context;

import com.tencent.mmkv.MMKV;

public class AppConfiguration implements AppInternalConfiguration, FunctionEntryConfiguration {

    private static volatile AppConfiguration INSTANCE;
    private final AppInternalConfiguration appInternalConfiguration;
    private final FunctionEntryConfiguration functionEntryConfiguration;

    public static AppConfiguration getDefault(Context context) {
        if (INSTANCE == null) {
            synchronized (AppConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppConfiguration(context);
                }
            }
        }
        return INSTANCE;
    }

    private AppConfiguration(Context context) {
        // /data/data/包名/files/mmkv/
        MMKV.initialize(context);
        appInternalConfiguration = new AppInternalConfigurationImp();
        functionEntryConfiguration = new FunctionEntryConfigurationImp();
    }

    @Override
    public void setTestA(boolean b) {
        appInternalConfiguration.setTestA(b);
    }

    @Override
    public boolean getTestA() {
        return appInternalConfiguration.getTestA();
    }

    @Override
    public void setTestB(boolean b) {
        functionEntryConfiguration.setTestB(b);
    }

    @Override
    public boolean getTestB() {
        return functionEntryConfiguration.getTestB();
    }

}
