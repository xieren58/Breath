package com.zkp.zkplib.utils;

import android.graphics.Paint;
import android.view.View;

/**
 * Created b Zwp on 2018/5/29.
 * @author zwp
 */

public class ViewLayerTypeAssist {

    // 关闭硬件加速
    public static void closeViewLevelHardware(View view,
                                              @androidx.annotation.Nullable Paint paint) {
        view.setLayerType(View.LAYER_TYPE_NONE, paint);
    }

    // 开启硬件加速
    public static void openViewLevelHardware(View view,
                                             @androidx.annotation.Nullable Paint paint) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }
}
