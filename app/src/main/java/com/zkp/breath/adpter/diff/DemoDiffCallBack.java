package com.zkp.breath.adpter.diff;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;


public class DemoDiffCallBack extends DiffUtil.ItemCallback<String> {

    private static final String TAG = DemoDiffCallBack.class.getSimpleName();

    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
        Log.i(TAG, "areItemsTheSame: oldItem: " + oldItem + ",newItem: " + newItem);
        // 模拟比较id，用内存地址代替
        return oldItem == newItem;
    }


    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
        Log.i(TAG, "areContentsTheSame: oldItem: " + oldItem + ",newItem: " + newItem);
        // 模拟比较内存
        return oldItem.equals(newItem);
    }

    /**
     * 可选实现，当areItemsTheSame返回true和areContentsTheSame返回false时会被回调。
     * 如果需要精确修改某一个view中的内容，请实现此方法。
     * 如果不实现此方法，或者返回null，将会直接刷新整个item。
     *
     * @param oldItem Old data
     * @param newItem New data
     * @return Payload info. if return null, the entire item will be refreshed.
     */
    @Override
    public Object getChangePayload(@NonNull String oldItem, @NonNull String newItem) {
        Log.i(TAG, "getChangePayload: oldItem: " + oldItem + ",newItem: " + newItem);
        return null;
    }
}
