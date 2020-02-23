package com.zkp.breath.review.threads.atomic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder在高并发的场景下比AtomicLong具有更好的性能，代价是消耗更多的内存空间。
 * 因为AtomicLong是采用自旋，在高并发（很多线程）会加大自旋的情况。
 * <p>
 * LongAdder的基本思路就是分散热点，将value值分散到一个数组中，不同线程会命中到数组的不同槽中，
 * 各个线程只对自己槽中的那个值进行CAS操作，这样热点就被分散了，冲突的概率就小很多。
 * 如果要获取真正的long值，只要将各个槽中的变量值累加返回
 * <p>
 * 总之，低并发、一般的业务场景下AtomicLong是足够了。如果并发量很多，存在大量写多读少的情况，那LongAdder可能更合适
 */
public class LongAdderDemo {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    longAdder.increment();
                }
            });
            list.add(thread);
            thread.start();
        }

        for (int i = 0; i < list.size(); i++) {
            Thread thread = list.get(i);
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("执行结果为：" + longAdder.toString());
    }


}
