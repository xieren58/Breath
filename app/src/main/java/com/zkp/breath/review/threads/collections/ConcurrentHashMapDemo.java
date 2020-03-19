package com.zkp.breath.review.threads.collections;

import java.util.concurrent.ConcurrentHashMap;

/**
 * https://segmentfault.com/a/1190000016096542
 * <p>
 * ConcurrentHashMap,并发安全的HashMap。
 * 线程安全：并发控制使用Synchronized和CAS来操作，如果table的index上没有节点的话就使用cas进行put，如果已经存
 *          在Node节点则用链表后者红黑树的头节点作为Synchronized的锁进行put操作(Hashtable的所有操作都用Synchronized加锁)
 * 数据结构：(数组+链表+红黑树，桶中的结构可能是链表，也可能是红黑树,红黑树是为了提高查找效率)
 * <p>
 * 构造器只是计算了下table的初始容量大小，并没有进行实际的创建table数组的工作,采用了一种“懒加载”的模式，
 * 只有到首次插入键值对的时候，才会真正的去初始化table数组。
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {
//        f1();
        f2();
    }

    private static void f1() {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("a", 1);
        concurrentHashMap.put("b", 2);
        concurrentHashMap.put("c", 3);

        Integer a = concurrentHashMap.get("a");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // 返回指定key对应的值；如果Map不存在该key，则返回defaultValue
            Integer d = concurrentHashMap.getOrDefault("d", 4);
        }

        // 如果Map不存在指定的key，则插入<K,V>；否则，直接返回该key对应的值
        Integer a1 = concurrentHashMap.putIfAbsent("a", 11);

        concurrentHashMap.put("d", 4);
        // 删除与<key,value>完全匹配的Entry，并返回true；否则，返回false
        boolean d = concurrentHashMap.remove("d", 4);

        // 如果存在key，且值和oldValue一致，则更新为newValue，并返回true；否则，返回false
        boolean a2 = concurrentHashMap.replace("a", 1, 11);
    }

    private static void f2() {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();

                System.out.println("进入" + name);
                concurrentHashMap.put("a", 1);

                try {
                    System.out.println(name + "准备进行sleep");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                concurrentHashMap.put("b", 2);
                concurrentHashMap.put("c", 3);
                System.out.println(name + "添加完毕:" + concurrentHashMap.size());
            }
        }, "线程A").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println("进入" + name);
                concurrentHashMap.put("d", 1);
                concurrentHashMap.put("e", 2);
                concurrentHashMap.put("f", 3);
                System.out.println(name + "添加完毕:" + concurrentHashMap.size());
            }
        }, "线程B").start();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("进入读取线程");
//                Set<Map.Entry<String, Integer>> entries = concurrentHashMap.entrySet();
//                Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
//                int i = 0;
//                while (iterator.hasNext()) {
//                    Map.Entry<String, Integer> next = iterator.next();
//                    System.out.println("遍历第" + i + "次_key: " + next.getKey() + "value: " + next.getValue());
//                    i++;
//                }
//            }
//        }).start();
    }

}
