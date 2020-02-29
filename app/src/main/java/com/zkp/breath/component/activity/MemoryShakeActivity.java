package com.zkp.breath.component.activity;

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
 * 内存抖动的Demo
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
