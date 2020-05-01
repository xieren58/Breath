package com.zkp.breath.adpter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.breath.R;

import java.util.List;
import java.util.Random;

public class ViewPagerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int[] colors = new int[]{0xFF008577, 0xFF03A9F4, 0xFFD81B60, 0xFFFF9800, 0xFF4CAF50, 0xFF673AB7};

    public ViewPagerAdapter(@Nullable List<String> data) {
        // 子view一定要match_parent
        // java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent)
        super(R.layout.adpter_viewpager2, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Random random = new Random();
        int i = random.nextInt(colors.length);
        helper.setText(R.id.tv, item);
        View convertView = helper.getConvertView();
        convertView.setBackgroundColor(colors[i]);
        Log.i("vp2", "convert: " + helper.getAdapterPosition() + ",背景图内存地址:" + convertView);
    }
}
