package com.zkp.breath.mmkv.template;

import com.tencent.mmkv.MMKV;

public class AppInternalConfigurationImp implements AppInternalConfiguration {

    private final MMKV mmkv;

    public AppInternalConfigurationImp() {
        mmkv = MMKV.mmkvWithID(ConfigurationField.ConfigurationFile.INTERNAL_CONFIGURATION);
    }

    @Override
    public void setTestA(boolean b) {
        mmkv.encode(ConfigurationField.InternalKey.TEST, b);
    }

    @Override
    public boolean getTestA() {
        return mmkv.decodeBool(ConfigurationField.InternalKey.TEST);
    }
}
