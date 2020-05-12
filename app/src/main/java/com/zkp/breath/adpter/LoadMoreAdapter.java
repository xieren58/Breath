package com.zkp.breath.adpter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zkp.breath.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LoadMoreAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    private int[] colors = new int[]{0xFF008577, 0xFF03A9F4, 0xFFD81B60, 0xFFFF9800, 0xFF4CAF50, 0xFF673AB7};

    public LoadMoreAdapter(@Nullable List<String> data) {
        super(R.layout.adpter_coordinator, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String item) {
        Random random = new Random();
        int i = random.nextInt(colors.length);
        baseViewHolder.setText(R.id.tv, item);
        View convertView = baseViewHolder.itemView;
        convertView.setBackgroundColor(colors[i]);
    }
}
