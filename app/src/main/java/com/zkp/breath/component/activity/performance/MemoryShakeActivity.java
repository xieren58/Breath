package com.zkp.breath.component.activity.performance;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zkp.breath.R;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * https://www.bilibili.com/video/BV1xf4y127Ur/?spm_id_from=trigger_reload。
 * <p>
 * 内存抖动（Memory Churn）：短时间内反复发生内存增长和回收的循环。
 * <p>
 * 造成内存抖动常见场景：
 * 1. onDraw()方法内部创建对象，因为onDraw()方法会被频繁调用，所以就会频繁创建对象。
 * 2. 在次数比较大的循环里创建对象。
 * <p>
 * 内存抖动造成的后果：
 * 1. 会造成界面卡顿。因为GC（Garbage Collection垃圾回收）是在主线程进行，而UI绘制也是在主线程进行，GC太频繁会
 * 导致UI绘制被延后这就导致界面出现视觉卡顿。
 * 2. 可能会造成内存溢出，因为回收来不及，这会导致应用崩溃。
 */
public class MemoryShakeActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_shake);
        mBind = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        imPrettySureSortingIsFree();
    }

    /**
     * 　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for (int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        for (int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j]; // 一直再new字符串
                if (j < (lotsOfInts[i].length - 1)) {
                    rowAsStr += ", ";
                }
            }
        }
    }

    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}
