package com.zkp.zkplib.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.WindowManager;

import java.util.Locale;

/**
 * Created b Zwp on 2018/11/15.
 */
public class CameraPageAssist {

    /**
     * 逆序
     * @param totalDuration 总时长,毫秒为单位
     * @param currentDuration 当前时长,毫秒为单位
     * @return  eg: "00 : 00"
     */
    public static String reverse(long totalDuration, long currentDuration) {

        currentDuration = totalDuration - currentDuration;

        long minute = (currentDuration / 60000) % 60;
        long second = (currentDuration / 1000) % 60;
        long millisecond = currentDuration % 1000;

        if (minute > 0) {
            millisecond = (millisecond * 100) / 1000;
            second = minute * 60 + second;
            return String.format(Locale.getDefault(), "%02d:%02d", second, millisecond);
        } else {
            millisecond = (millisecond * 100) / 1000;
            return String.format(Locale.getDefault(), "%02d:%02d", second, millisecond);
        }
    }

    /**
     * 顺序
     * @param currentDuration 当前时长,毫秒为单位
     * @return  eg: "00 : 00"
     */
    public static String order(long currentDuration) {
        long minute = (currentDuration / 60000) % 60;
        long second = (currentDuration / 1000) % 60;
        long millisecond = currentDuration % 1000;

        if (minute > 0) {
            millisecond = (millisecond * 100) / 1000;
            second = minute * 60 + second;
            return String.format(Locale.getDefault(), "%02d:%02d", second, millisecond);
        } else {
            millisecond = (millisecond * 100) / 1000;
            return String.format(Locale.getDefault(), "%02d:%02d", second, millisecond);
        }
    }

    /**
     * 是否屏幕常亮
     * @param wakeup true为常亮;false反之
     */
    public static void keepScreenWakeUp(Context context, boolean wakeup) {
        if (wakeup) {
            ((Activity) context).getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else if (!wakeup) {
            ((Activity) context).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * 是否静音
     *
     * @see AudioManager
     * @see AudioManager#STREAM_MUSIC
     * @return true为静音;false反之
     */
    public static boolean isMusicVolumeMute(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0;
    }
}
