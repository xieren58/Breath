package com.zkp.breath.mmkv.template;

import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

public class MMKVHandlerLogcat implements MMKVHandler {
    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String s) {
        return null;
    }

    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String s) {
        return null;
    }

    @Override
    public boolean wantLogRedirecting() {
        return false;
    }

    @Override
    public void mmkvLog(MMKVLogLevel mmkvLogLevel, String s, int i, String s1, String s2) {

    }
}
