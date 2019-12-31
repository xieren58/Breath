package com.zkp.breath.mmkv.template;

import com.tencent.mmkv.MMKV;

public class FunctionEntryConfigurationImp implements FunctionEntryConfiguration {

    private final MMKV mmkv;

    public FunctionEntryConfigurationImp() {
        mmkv = MMKV.mmkvWithID(ConfigurationField.ConfigurationFile.FUNCTION_ENTRY_CONFIGURATION);
    }

    @Override
    public void setTestB(boolean b) {
        mmkv.encode(ConfigurationField.FunctionEntryKey.TEST, b);
    }

    @Override
    public boolean getTestB() {
        return mmkv.decodeBool(ConfigurationField.FunctionEntryKey.TEST);
    }
}
