package com.zkp.breath.mmkv.template;

import android.content.Context;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        // 接收转发mmkv的日志
        MMKV.registerHandler(new MMKVHandlerLogcat());
        // 设置日志的等级
        MMKV.setLogLevel(MMKVLogLevel.LevelInfo);
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
    public void removeTestA() {
        appInternalConfiguration.removeTestA();
    }

    @Override
    public boolean containsKeyTestA() {
        return appInternalConfiguration.containsKeyTestA();
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
