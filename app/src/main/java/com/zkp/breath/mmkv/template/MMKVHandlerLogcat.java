package com.zkp.breath.mmkv.template;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

public class MMKVHandlerLogcat implements MMKVHandler {

    /**
     * 在 crc 校验失败，或者文件长度不对的时候，MMKV 默认会丢弃所有数据。你可以让 MMKV 恢复数据。
     * 要注意的是修复率无法保证，而且可能修复出奇怪的 key-value
     */
    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    /**
     * 在 crc 校验失败，或者文件长度不对的时候，MMKV 默认会丢弃所有数据。你可以让 MMKV 恢复数据。
     * 要注意的是修复率无法保证，而且可能修复出奇怪的 key-value
     */
    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public boolean wantLogRedirecting() {
        return true;
    }

    /**
     * 选择合适的log工具进行打印
     */
    @Override
    public void mmkvLog(MMKVLogLevel mmkvLogLevel, String file, int line, String func, String message) {
        String log = "<" + file + ":" + line + "::" + func + "> " + message;
        switch (mmkvLogLevel) {
            case LevelDebug:
            case LevelNone:
                //Log.e("redirect logging MMKV", log);
                //                Log.d("redirect logging MMKV", log);
                LogUtils.d("MMKV_log: " + log);
                break;
            case LevelInfo:
                //Log.i("redirect logging MMKV", log);
                LogUtils.i("MMKV_log: " + log);
                break;
            case LevelWarning:
                //Log.w("redirect logging MMKV", log);
                LogUtils.w("MMKV_log: " + log);
                break;
            case LevelError:
                //Log.e("redirect logging MMKV", log);
                LogUtils.e("MMKV_log: " + log);
                break;
        }
    }
}
