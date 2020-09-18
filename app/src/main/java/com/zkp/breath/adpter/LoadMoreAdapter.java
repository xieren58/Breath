package com.zkp.breath.adpter;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zkp.breath.R;

import java.util.List;
import java.util.Random;

public class LoadMoreAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    private static final String TAG = LoadMoreAdapter.class.getSimpleName();
    private int[] colors = new int[]{0xFF008577, 0xFF03A9F4, 0xFFD81B60, 0xFFFF9800, 0xFF4CAF50, 0xFF673AB7};

    public LoadMoreAdapter(@Nullable List<String> data) {
        super(R.layout.adpter_coordinator, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String item) {
        Random random = new Random();
        int i = random.nextInt(colors.length);
        baseViewHolder.setText(R.id.tv, item);
        View convertView = baseViewHolder.itemView;
        convertView.setBackgroundColor(colors[i]);
        Log.i(TAG, "convert: " + baseViewHolder.getAdapterPosition());
    }

    /**
     * 刷新某个item的某些子view，真正做到局部刷新。使用adapter.notifyItemChanged（int position, @Nullable Object payload）
     * 触发，实际是参数二发挥效果，类似一种标记作用。
     */
    @Override
    protected void convert(BaseViewHolder holder, String item, List<?> payloads) {
        super.convert(holder, item, payloads);
        Log.i(TAG, "convert_payloads: " + holder.getAdapterPosition() + ",payloads: " + payloads.get(0));
    }

    // notifyDataSetChanged执行后可能所有动画一起执行，看不出一种先后顺序，可以重写下面的方法添加延时就可以看到动画的顺序执行了
//    @Override
//    protected void startAnim(@NotNull Animator anim, int index) {
//        anim.setStartDelay(index * 150);
//        super.startAnim(anim, index);
//    }

}
