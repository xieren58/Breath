package com.zkp.breath.review.threads.collections;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList是并发安全的ArrayList。
 * <p>
 * 运用了一种“写时复制”的思想，修改的时候不是在原列表上直接修改，而是先将列表Copy，然后在新的副本上进行修改，
 * 修改完成之后，再将引用从原列表指向新列表。这样做的好处是读/写是不会冲突的，可以并发进行，读操作还是在原列表，
 * 写操作在新列表。仅仅当有多个线程同时进行写操作时，才会进行同步。
 * <p>
 * 注意：
 * 1.内部ReentrantLock排它锁, 用于同步修改操作。（ReentrantReadWriteLock读写会冲突，所以不使用）。
 * 2.内存的使用：由于CopyOnWriteArrayList使用了“写时复制”，所以在进行写操作的时候，内存里会同时存在两个array数组，
 * 如果数组内存占用的太大，那么可能会造成频繁GC,所以CopyOnWriteArrayList并不适合大数据量的场景，适合存放小量数据
 * 且“读多写少”的场景。
 * 3.数据一致性：CopyOnWriteArrayList只能保证数据的最终一致性，不能保证数据的实时一致性——读操作读到的数据只是一份快照。
 * 所以如果希望写入的数据可以立刻被读到，那CopyOnWriteArrayList并不适合。
 */
public class CopyOnWriteArrayListDemo {

    /**
     * 一般来说CopyOnWriteArrayList并不能保证数据的实时一致性，但下面一致在获取确实能获取修改后的值，但不可能会
     * 有程序这么设计。
     */
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("a");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("读线程:" + copyOnWriteArrayList.get(0));
                }
            }
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("睡眠结束");

        new Thread(new Runnable() {
            @Override
            public void run() {
                copyOnWriteArrayList.set(0, "b");
            }
        }).start();
    }
}
