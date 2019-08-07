package com.zkp.breath.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created b Zwp on 2019/8/7.
 * <p>AtomicReference提供了以无锁方式访问共享资源的能力,从而保证线程安全。</p>
 *
 * @author zwp
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> ref = new AtomicReference<>(new Integer(1000));

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Task(ref), "Thread-" + i);
            list.add(t);
            t.start();
        }

        // 主线程彻底让出CPU执行权给子线程
        for (Thread t : list) {
            t.join();
        }

        // 所有子线程都执行完毕后才会执行主线程这句代码
        System.out.println(ref.get());    // 打印2000
    }
}

class Task implements Runnable {

    // 原子类（共享资源）
    private AtomicReference<Integer> ref;

    Task(AtomicReference<Integer> ref) {
        this.ref = ref;
    }

    @Override
    public void run() {
        // 解析：假如A线程在1，B线程进入了123后死亡，A线程又抢到执行权执行到2判断失败则再次for循环重新判断。

        // 使用自旋+CAS的无锁操作保证共享变量的线程安全
        for (; ; ) {
            //自旋操作
            Integer oldV = ref.get();// 1
            // CAS操作
            if (ref.compareAndSet(oldV, oldV + 1)) {// 2
                break;// 3
            }
        }
    }
}

