package com.zkp.breath.leakcanary2;

import android.content.Context;

public class LeakCanarySingleInstance {

    private Context context;

    private LeakCanarySingleInstance(Context context) {
        this.context = context;
    }

    public static class Holder {
        private static LeakCanarySingleInstance INSTANCE;

        public static LeakCanarySingleInstance newInstance(Context context) {
            if (INSTANCE == null) {
                INSTANCE = new LeakCanarySingleInstance(context);
            }
            return INSTANCE;
        }
    }

}
