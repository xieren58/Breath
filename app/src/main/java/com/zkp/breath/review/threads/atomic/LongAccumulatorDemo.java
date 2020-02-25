package com.zkp.breath.review.threads.atomic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

/**
 * LongAccumulator
 * LongAdder类是LongAccumulator的一个特例，LongAccumulator提供了比LongAdder更强大的功能，如下构造函数，
 * 其中accumulatorFunction是一个双目运算器接口，根据输入的两个参数返回一个计算值，identity则是LongAccumulator
 * 累加器的初始值。
 * <p>
 * 总结：LongAccumulator相比LongAdder 可以提供累加器初始非0值，后者只能默认为0，另外前者还可以指定累加规则，
 * 比如不是累加而相乘，只需要构造LongAccumulator 时候传入自定义双目运算器即可，后者则内置累加规则。
 */
public class LongAccumulatorDemo {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        // accumulate方法调用是接口实现的方法
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left * right;
            }
        }, 2);

        longAccumulator.accumulate(2);

        System.out.println("结果：" + longAccumulator.toString());

    }

}
