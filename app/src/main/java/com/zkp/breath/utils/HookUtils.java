package com.zkp.breath.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * https://blog.csdn.net/EthanCo/article/details/111544333
 * <p>
 * 作用：用于判断是否获取了mac等敏感信息。
 * <p>
 * 原理：使用反射的方式获取到WifiManager实例（唯一），再使用代理的方式当调用getConnectionInfo()方法的时候就会
 * 触发InvocationHandler#invoke()，所以在里面打印日志就能知道是否调用了该方法。
 */
public class HookUtils {

    private static WifiInfo cacheWifiInfo = null;

    public static void hookMacAddress(String tag, Context context) {
        try {
            Class<?> iWifiManager = Class.forName("android.net.wifi.IWifiManager");
            Field serviceField = WifiManager.class.getDeclaredField("mService");
            serviceField.setAccessible(true);

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // real mService
            Object realIwm = serviceField.get(wifi);
            // replace mService  with Proxy.newProxyInstance
            serviceField.set(wifi, Proxy.newProxyInstance(iWifiManager.getClassLoader(),
                    new Class[]{iWifiManager},
                    new InvocationHandler(tag, "getConnectionInfo", realIwm)));
            Log.i(tag, "wifiManager hook success");
        } catch (Exception e) {
            Log.e(tag, "printStackTrace:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取调用该方法的方法栈，即流程链
     */
    private static void printStackTrace(String tag) {
        Exception exception = new Exception();
        StackTraceElement[] stackTrace = exception.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            Log.i(tag, "printStackTrace: " + stackTraceElement);
        }
    }

    public static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        private final String tag;
        private final String methodName;
        private Object real;

        public InvocationHandler(String tag, String methodName, Object real) {
            this.real = real;
            this.methodName = methodName;
            this.tag = tag;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(tag, "method invoke " + method.getName());
            if (methodName.equals(method.getName())) {
                if (cacheWifiInfo != null) {
                    Log.d(tag, "cacheWifiInfo:" + cacheWifiInfo);
                    printStackTrace(tag);
                    return cacheWifiInfo;
                }
                WifiInfo wifiInfo = null;
                try {
                    Class cls = WifiInfo.class;
                    wifiInfo = (WifiInfo) cls.newInstance();
                    Field mMacAddressField = cls.getDeclaredField("mMacAddress");
                    mMacAddressField.setAccessible(true);
                    mMacAddressField.set(wifiInfo, "");
                    cacheWifiInfo = wifiInfo;
                    Log.d(tag, "wifiInfo:" + wifiInfo);
                    printStackTrace(tag);
                } catch (Exception e) {
                    Log.e(tag, "WifiInfo error:" + e.getMessage());
                }
                return wifiInfo;
            } else {
                return method.invoke(real, args);
            }
        }

    }
}
