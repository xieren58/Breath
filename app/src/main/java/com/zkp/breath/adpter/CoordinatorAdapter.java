package com.zkp.breath.adpter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zkp.breath.R;

import java.util.List;
import java.util.Random;

public class CoordinatorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int[] colors = new int[]{0xFF008577, 0xFF03A9F4, 0xFFD81B60, 0xFFFF9800, 0xFF4CAF50, 0xFF673AB7};

    public CoordinatorAdapter(@Nullable List<String> data) {
        super(R.layout.adpter_coordinator, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Random random = new Random();
        int i = random.nextInt(colors.length);
        helper.setText(R.id.tv, item);
        View convertView = helper.itemView;
        convertView.setBackgroundColor(colors[i]);
    }
}
