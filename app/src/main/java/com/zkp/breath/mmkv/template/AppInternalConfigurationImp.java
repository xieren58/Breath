package com.zkp.breath.mmkv.template;

import com.tencent.mmkv.MMKV;

public class AppInternalConfigurationImp implements AppInternalConfiguration {

    private final MMKV mmkv;
    private static final String ENCRYPT_KEY = AppInternalConfigurationImp.class.getSimpleName();

    public AppInternalConfigurationImp() {
        mmkv = MMKV.mmkvWithID(ConfigurationField.ConfigurationFile.INTERNAL_CONFIGURATION,
                MMKV.SINGLE_PROCESS_MODE, ENCRYPT_KEY);
        // 取消加密
        mmkv.reKey(null);
    }

    @Override
    public void setTestA(boolean b) {
        mmkv.encode(ConfigurationField.InternalKey.TEST, b);
    }

    @Override
    public boolean getTestA() {
        return mmkv.decodeBool(ConfigurationField.InternalKey.TEST);
    }

    @Override
    public void removeTestA() {
        mmkv.remove(ConfigurationField.InternalKey.TEST);
    }

    @Override
    public boolean containsKeyTestA() {
        return mmkv.containsKey(ConfigurationField.InternalKey.TEST);
    }
}
